package com.cqupt.knowtolearn.model.dto.req;

/**
 * @author Ray
 * @date 2023/8/1 17:34
 * @description
 */
public class CommentReq {

    private Integer courseId;

    private Integer parentId;

    private String content;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
