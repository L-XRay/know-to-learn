package com.cqupt.knowtolearn.controller;

import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.model.dto.req.LoginReq;
import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.service.user.IUserService;
import com.cqupt.knowtolearn.utils.UserHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody LoginReq loginReq) {
        LoginRes res = userService.login(loginReq);
        if (res != null) {
            String userAgent = request.getHeader("User-Agent");
            UserHolder.saveUserAgent(userAgent);
            return Result.success("登录成功", res);
        }
        return Result.fail("登录失败");
    }

}
