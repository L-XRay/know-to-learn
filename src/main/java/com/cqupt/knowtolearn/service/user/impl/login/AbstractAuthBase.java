package com.cqupt.knowtolearn.service.user.impl.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.knowtolearn.dao.user.IUserDao;
import com.cqupt.knowtolearn.exception.KnowException;
import com.cqupt.knowtolearn.model.dto.UserDTO;
import com.cqupt.knowtolearn.model.dto.req.LoginReq;
import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.utils.JwtUtil;
import com.cqupt.knowtolearn.utils.PasswordUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ray
 * @date 2023/7/27 14:52
 * @description
 */
public abstract class AbstractAuthBase implements IAuthService {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private IUserDao userDao;

    @Resource
    protected PasswordUtil passwordUtil;

    @Override
    public abstract LoginRes auth(LoginReq loginReq);

    protected LoginRes getLoginRes(User user) {
        Map<String,Object> map = new HashMap<>();
        String username = user.getUsername();
        String nickname = user.getNickname();
        String email = user.getEmail();
        String avatar = user.getAvatar();
        map.put("username", username);
        map.put("email", email);
        map.put("nickname", nickname);
        map.put("avatar", nickname);
        String token = jwtUtil.encodeToken(map);
        UserDTO userDTO = new UserDTO(username, nickname,avatar, email);
        LoginRes res = new LoginRes(userDTO, token);
//        UserHolder.saveUser(res);
        return res;
    }

    protected User getUserByUsername(String username) {
        return userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    protected User registerEmailUser(String email){
        User registerUser = new User();
        registerUser.setEmail(email);
//            registerUser.setPassword(passwordEncoder.encode("123456"));
        registerUser.setPassword(null);
        registerUser.setUsername(email);
        registerUser.setNickname(email);
        registerUser.setStatus("1");
        registerUser.setCreateTime(LocalDateTime.now());
        int insert = userDao.insert(registerUser);
        if(insert<=0) {
            throw new KnowException("注册失败");
        }
        return registerUser;
    }


}
