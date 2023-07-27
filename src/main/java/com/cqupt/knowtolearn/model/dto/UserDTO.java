package com.cqupt.knowtolearn.model.dto;

/**
 * @author Ray
 * @date 2023/7/27 08:46
 * @description
 */
public class UserDTO {

    private String username;

    private String nickname;

    private String avatar;

    private String email;

    public UserDTO() {
    }

    public UserDTO(String username, String nickname, String avatar, String email) {
        this.username = username;
        this.nickname = nickname;
        this.avatar = avatar;
        this.email = email;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
