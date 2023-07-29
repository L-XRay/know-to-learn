package com.cqupt.knowtolearn.model.dto.res;

import com.cqupt.knowtolearn.model.dto.UserDTO;

/**
 * @author Ray
 * @date 2023/7/26 19:07
 * @description
 */
public class LoginRes {

    private UserDTO userInfo;

    private String token;

    private String refreshToken;

    public LoginRes() {
    }

    public LoginRes(UserDTO userInfo, String token, String refreshToken) {
        this.userInfo = userInfo;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public UserDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserDTO userInfo) {
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
