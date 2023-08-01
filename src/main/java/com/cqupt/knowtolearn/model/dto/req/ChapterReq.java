package com.cqupt.knowtolearn.model.dto.req;

/**
 * @author Ray
 * @date 2023/8/1 10:22
 * @description
 */
public class ChapterReq {

    private Integer courseId;

    private String chapterName;

    private Integer orderBy;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
