package com.cqupt.knowtolearn.service.user.impl.login;

import com.cqupt.knowtolearn.model.dto.req.LoginReq;
import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.model.po.user.User;

/**
 * @author Ray
 * @date 2023/7/27 14:43
 * @description
 */
public interface IAuthService {

    LoginRes auth(LoginReq loginReq);
}
