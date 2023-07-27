package com.cqupt.knowtolearn.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.dao.user.IUserDao;
import com.cqupt.knowtolearn.model.dto.UserDTO;
import com.cqupt.knowtolearn.model.dto.req.LoginReq;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.service.captcha.ICaptchaService;
import com.cqupt.knowtolearn.service.user.IUserService;
import com.cqupt.knowtolearn.utils.JwtUtil;
import com.cqupt.knowtolearn.utils.PasswordUtil;
import com.cqupt.knowtolearn.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
* @author Ray
* @date 2023-07-26
* @description  服务实现
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<IUserDao, User> implements IUserService {

    @Resource
    private IUserDao userDao;

    @Resource
    private ICaptchaService emailCaptchaService;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private PasswordUtil passwordUtil;

    @Override
    public LoginRes login(LoginReq req) {
        if ("email".equals(req.getType())) {
            String email = req.getUsername();
            String code = req.getCode();
            if(StringUtils.isEmpty(code)){
                throw new RuntimeException("请输入邮箱验证码");
            }
            boolean verify = emailCaptchaService.verify("email:" + email, code);
            if (!verify) {
                throw new RuntimeException("邮箱验证码错误");
            }
            // 判断账号是否存在
            String username = req.getUsername();
            User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
            if (user==null) {
                User registerUser = new User();
                registerUser.setEmail(email);
//            registerUser.setPassword(passwordEncoder.encode("123456"));
                registerUser.setPassword(null);
                registerUser.setUsername(email);
                registerUser.setNickname("注册用户");
                registerUser.setStatus("1");
                registerUser.setCreateTime(LocalDateTime.now());
                int insert = userDao.insert(registerUser);
                if(insert<=0) {
                    throw new RuntimeException("注册失败");
                }
                return getLoginRes(registerUser);
            }
            return getLoginRes(user);
        } else {
            String username = req.getUsername();
            String password = req.getPassword();
            // 判断账号是否存在
            User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
            if (user == null) {
                throw new RuntimeException("用户不存在");
            }
            if (!passwordUtil.matches(password,user.getPassword(),user.getSalt())) {
                throw new RuntimeException("密码不正确");
            }
            return getLoginRes(user);
        }
    }

    private LoginRes getLoginRes(User user) {
        Map<String,Object> map = new HashMap<>();
        String username = user.getUsername();
        String nickname = user.getNickname();
        String email = user.getEmail();
        map.put("username", username);
        map.put("email", email);
        map.put("nickname", nickname);
        String token = jwtUtil.encodeToken(map);
        UserDTO userDTO = new UserDTO(username, nickname, email);
        LoginRes res = new LoginRes(userDTO, token);
//        UserHolder.saveUser(res);
        return res;
    }
}
