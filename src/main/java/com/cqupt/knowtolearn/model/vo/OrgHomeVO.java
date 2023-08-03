package com.cqupt.knowtolearn.model.vo;

import java.util.List;

/**
 * @author Ray
 * @date 2023/8/3 09:40
 * @description
 */
public class OrgHomeVO {

    private Integer orgId;

    private Integer userId;

    private String orgName;

    private String introduction;

    private String materials;

    List<OrgCourseVO> courses;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public List<OrgCourseVO> getCourses() {
        return courses;
    }

    public void setCourses(List<OrgCourseVO> courses) {
        this.courses = courses;
    }
}
