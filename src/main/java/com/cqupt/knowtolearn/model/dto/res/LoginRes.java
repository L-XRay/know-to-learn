package com.cqupt.knowtolearn.model.dto.res;

/**
 * @author Ray
 * @date 2023/7/26 19:07
 * @description
 */
public class LoginRes {

    private String username;

    private String nickname;

    private String email;

    private String token;

    public LoginRes() {
    }

    public LoginRes(String username, String nickname, String email, String token) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
