package com.cqupt.knowtolearn.model.vo;

import java.time.LocalDateTime;

/**
 * @author Ray
 * @date 2023/7/28 23:32
 * @description
 */
public class HomeCourseVO {

    private String coverUrl;

    private String courseName;

    private String orgName;

    private LocalDateTime publishTime;

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }
}
