package com.cqupt.knowtolearn.model.vo;

/**
 * @author Ray
 * @date 2023/8/7 09:41
 * @description
 */
public class QueryOrgVO {

    private Integer id;

    private String name;

    private String avatar;

    private String intro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
