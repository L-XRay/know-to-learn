package com.cqupt.knowtolearn.controller;

import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.model.dto.req.LoginReq;
import com.cqupt.knowtolearn.model.dto.res.CosRes;
import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.model.vo.StationMessageVO;
import com.cqupt.knowtolearn.model.vo.UserVO;
import com.cqupt.knowtolearn.service.system.IStationMessageService;
import com.cqupt.knowtolearn.service.system.impl.CosService;
import com.cqupt.knowtolearn.service.user.IUserService;
import com.cqupt.knowtolearn.utils.JwtUtil;
import com.cqupt.knowtolearn.utils.UserHolder;
import com.qcloud.cos.http.HttpMethodName;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ray
 * @date 2023/7/26 17:30
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private CosService cosService;

    @Resource
    private IStationMessageService stationMessageService;

    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody LoginReq loginReq) {
        LoginRes res = userService.login(loginReq);
        if (res != null) {
//            String userAgent = request.getHeader("User-Agent");
//            UserHolder.saveUserAgent(userAgent);
            return Result.success("登录成功", res);
        }
        return Result.fail("登录失败");
    }

    @GetMapping("/personal")
    public Result get(HttpServletRequest request) {
//        String token = request.getHeader("Authorization").substring(7);
        UserVO user = userService.findUserByUserId(UserHolder.getUser());
        if (user != null) {
            return Result.success("获取个人信息成功", user);
        }
        return Result.fail("获取个人信息失败");
    }

    @PostMapping("/update/password")
    public Result updatePassword(HttpServletRequest request,@RequestBody Map<String,String> req) {
        String password = req.get("password");
//        String token = request.getHeader("Authorization").substring(7);
        userService.updatePassword(UserHolder.getUser(),password);
        return Result.success(200,"修改密码成功");
    }

    @PostMapping("/update/username")
    public Result updateUsername(HttpServletRequest request,@RequestBody Map<String,String> req) {
        String username = req.get("username");
//        String token = request.getHeader("Authorization").substring(7);
        userService.updateUsername(UserHolder.getUser(),username);
        return Result.success(200,"修改用户名成功");
    }

    @PostMapping("/update/nickname")
    public Result updateNickname(HttpServletRequest request,@RequestBody Map<String,String> req) {
        String nickname = req.get("nickname");
//        String token = request.getHeader("Authorization").substring(7);
        userService.updateNickname(UserHolder.getUser(),nickname);
        return Result.success(200,"修改昵称成功");
    }

    @PostMapping("/upload/avatar")
    public Result uploadAvatar(HttpServletRequest request,@RequestBody Map<String,String> req) {
        String suffix = req.get("suffix");
        String token = request.getHeader("Authorization").substring(7);
        String userId = (String) jwtUtil.decodeToken(token).get("id");
        CosRes cosRes = cosService.getAvatarSignature(HttpMethodName.PUT, Integer.valueOf(userId), suffix);
        return Result.success("获取COS签名URL成功",cosRes);
    }

    @PostMapping("/refresh/token")
    public Result refreshToken(HttpServletRequest request,@RequestBody Map<String,String> req) {
        String refreshToken = req.get("refreshToken");
        String id = (String) jwtUtil.decodeToken(refreshToken).get("id");
//        if (UserHolder.getUser().equals(username)) {
//            User user = userService.findUserByUsername(username);
            Map<String,Object> map = new HashMap<>();
//            String id = String.valueOf(user.getId());
            map.put("id",id);
//            map.put("username", username);
            Map<String, String> token = jwtUtil.generateToken(map);
            return Result.success("刷新token成功",token);
//        }
//        return Result.fail("刷新token失败");
    }

    @GetMapping("/stationMessage/all")
    public Result getStationMessage(HttpServletRequest request) {
        List<StationMessageVO> data = stationMessageService.listMessage(UserHolder.getUser());
        return Result.success("获取站内信成功",data);
    }

    @GetMapping("/stationMessage/count")
    public Result getStationMessageCount(HttpServletRequest request) {
        Long data = stationMessageService.getNoReadCount(UserHolder.getUser());
        return Result.success("获取未读站内信个数成功",data);
    }

}
