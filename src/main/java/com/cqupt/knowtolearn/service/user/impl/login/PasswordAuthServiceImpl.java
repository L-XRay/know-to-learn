package com.cqupt.knowtolearn.service.user.impl.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.knowtolearn.dao.user.IUserDao;
import com.cqupt.knowtolearn.exception.KnowException;
import com.cqupt.knowtolearn.model.dto.req.LoginReq;
import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.utils.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Ray
 * @date 2023/7/27 14:42
 * @description
 */
@Service("passwordAuthServiceImpl")
public class PasswordAuthServiceImpl extends AbstractAuthBase {

    @Override
    public LoginRes auth(LoginReq req) {
        String username = req.getUsername();
        String password = req.getPassword();
        // 判断账号是否存在
        User user = getUserByUsername(username);
        if (user == null) {
            throw new KnowException("用户不存在");
        }
        if (!passwordUtil.matches(password,user.getPassword(),user.getSalt())) {
            throw new KnowException("密码不正确");
        }
        return getLoginRes(user);
    }
}
