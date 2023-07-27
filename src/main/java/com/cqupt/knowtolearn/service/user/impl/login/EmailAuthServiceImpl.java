package com.cqupt.knowtolearn.service.user.impl.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.knowtolearn.dao.user.IUserDao;
import com.cqupt.knowtolearn.exception.KnowException;
import com.cqupt.knowtolearn.model.dto.req.LoginReq;
import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.service.captcha.ICaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author Ray
 * @date 2023/7/27 14:47
 * @description
 */
@Service("emailServiceImpl")
public class EmailAuthServiceImpl extends AbstractAuthBase {

    @Resource
    private ICaptchaService emailCaptchaService;

    @Resource
    private IUserDao userDao;

    @Override
    public LoginRes auth(LoginReq req) {
        String email = req.getUsername();
        String code = req.getCode();
        if(StringUtils.isEmpty(code)){
            throw new KnowException("请输入邮箱验证码");
        }
        boolean verify = emailCaptchaService.verify("email:" + email, code);
        if (!verify) {
            throw new KnowException("邮箱验证码错误");
        }
        // 判断账号是否存在
        String username = req.getUsername();
        User user = getUserByUsername(username);
        if (user==null) {
            return getLoginRes(registerEmailUser(email));
        }
        return getLoginRes(user);
    }
}
