package com.cqupt.knowtolearn.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.knowtolearn.model.dto.req.LoginReq;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.model.vo.UserVO;

/**
* @author Ray
* @date 2023-07-26
* @description  服务接口
*/
public interface IUserService {

    LoginRes login(LoginReq req);

    UserVO findUserByUsername(String token);

    void updatePassword(String token, String password);

    UserVO updateUsername(String token, String username);

    UserVO updateNickname(String token, String nickname);

}
