package com.cqupt.knowtolearn.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.knowtolearn.model.dto.req.LoginReq;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.model.dto.res.LoginRes;

/**
* @author Ray
* @date 2023-07-26
* @description  服务接口
*/
public interface IUserService {

    LoginRes login(LoginReq req);
}
