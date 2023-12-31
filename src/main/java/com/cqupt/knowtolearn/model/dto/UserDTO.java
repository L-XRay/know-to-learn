package com.cqupt.knowtolearn.model.dto;

/**
 * @author Ray
 * @date 2023/7/27 08:46
 * @description
 */
public class UserDTO {

    private Integer id;

    private String username;

    private String nickname;

    private String avatar;

    private String role;

    private String email;

    public UserDTO() {
    }

    public UserDTO(Integer id, String username, String nickname, String avatar, String email,String role) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.avatar = avatar;
        this.email = email;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
