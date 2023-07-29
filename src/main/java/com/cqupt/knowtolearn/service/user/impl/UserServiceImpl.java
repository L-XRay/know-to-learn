package com.cqupt.knowtolearn.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.dao.user.IUserDao;
import com.cqupt.knowtolearn.exception.KnowException;
import com.cqupt.knowtolearn.model.dto.UserDTO;
import com.cqupt.knowtolearn.model.dto.req.LoginReq;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.model.vo.UserVO;
import com.cqupt.knowtolearn.service.captcha.ICaptchaService;
import com.cqupt.knowtolearn.service.user.IUserService;
import com.cqupt.knowtolearn.service.user.impl.login.LoginStrategy;
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
public class UserServiceImpl extends LoginStrategy implements IUserService {

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
//        if ("email".equals(req.getType())) {
//            String email = req.getUsername();
//            String code = req.getCode();
//            if(StringUtils.isEmpty(code)){
//                throw new RuntimeException("请输入邮箱验证码");
//            }
//            boolean verify = emailCaptchaService.verify("email:" + email, code);
//            if (!verify) {
//                throw new RuntimeException("邮箱验证码错误");
//            }
//            // 判断账号是否存在
//            String username = req.getUsername();
//            User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
//            if (user==null) {
//                User registerUser = new User();
//                registerUser.setEmail(email);
////            registerUser.setPassword(passwordEncoder.encode("123456"));
//                registerUser.setPassword(null);
//                registerUser.setUsername(email);
//                registerUser.setNickname("注册用户");
//                registerUser.setStatus("1");
//                registerUser.setCreateTime(LocalDateTime.now());
//                int insert = userDao.insert(registerUser);
//                if(insert<=0) {
//                    throw new KnowException("注册失败");
//                }
//                return getLoginRes(registerUser);
//            }
//            return getLoginRes(user);
//        } else {
//            String username = req.getUsername();
//            String password = req.getPassword();
//            // 判断账号是否存在
//            User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
//            if (user == null) {
//                throw new KnowException("用户不存在");
//            }
//            if (!passwordUtil.matches(password,user.getPassword(),user.getSalt())) {
//                throw new KnowException("密码不正确");
//            }
//            return getLoginRes(user);
//        }
        return authServiceMap.get(req.getType()).auth(req);
    }

//    private LoginRes getLoginRes(User user) {
//        Map<String,Object> map = new HashMap<>();
//        String username = user.getUsername();
//        String nickname = user.getNickname();
//        String email = user.getEmail();
//        String avatar = user.getAvatar();
//        String role = user.getRole();
//        map.put("username", username);
//        map.put("email", email);
//        map.put("nickname", nickname);
//        map.put("avatar", nickname);
//        map.put("role",role);
//        String token = jwtUtil.encodeToken(map);
//        UserDTO userDTO = new UserDTO(username, nickname,avatar, email,role);
//        LoginRes res = new LoginRes(userDTO, token);
////        UserHolder.saveUser(res);
//        return res;
//    }

    @Override
    public UserVO findUserByUserId(Integer userId) {
//        Integer userId = getCurrentUserId(token);
        User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, userId));
        if (user==null) {
            throw new KnowException("用户不存在");
        }
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setAvatar(user.getAvatar());
        userVO.setEmail(user.getEmail());
        userVO.setNickname(user.getNickname());
        userVO.setUsername(user.getUsername());
        userVO.setPassword(user.getPassword()!=null);
        userVO.setRole(user.getRole());
        userVO.setSalt(user.getSalt());
        userVO.setOrgId(user.getCompanyId());
        return userVO;
    }

    @Override
    public void updatePassword(Integer userId, String inputPassword) {
//        Integer userId = getCurrentUserId(token);
        try {
            User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, userId));
            String salt = passwordUtil.generateSalt();
            String encryptPassword = passwordUtil.encryptPassword(inputPassword, salt);
            user.setPassword(encryptPassword);
            user.setSalt(salt);
            userDao.updateById(user);
        } catch (Exception e) {
            throw new KnowException("修改密码失败");
        }
    }

    @Override
    public UserVO updateUsername(Integer userId, String username) {
//        Integer userId = getCurrentUserId(token);
        try {
            User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, userId));
            user.setUsername(username);
            userDao.updateById(user);
        } catch (Exception e) {
            throw new KnowException("修改用户名失败");
        }
        return null;
    }

    @Override
    public UserVO updateNickname(Integer userId, String nickname) {
//        Integer userId = getCurrentUserId(token);
        try {
            User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, userId));
            user.setNickname(nickname);
            userDao.updateById(user);
        } catch (Exception e) {
            throw new KnowException("修改昵称失败");
        }
        return null;
    }

    private Integer getCurrentUserId(String token) {
        String id = (String) jwtUtil.decodeToken(token).get("id");
        return Integer.valueOf(id);
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        return user;
    }
}
