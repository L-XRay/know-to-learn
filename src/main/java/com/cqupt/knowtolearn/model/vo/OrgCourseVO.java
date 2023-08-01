package com.cqupt.knowtolearn.model.vo;

/**
 * @author Ray
 * @date 2023/8/1 16:28
 * @description
 */
public class OrgCourseVO {

    private Integer courseId;

    private String courseName;

    private String coverUrl;

    private Long publishTime;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }
}
