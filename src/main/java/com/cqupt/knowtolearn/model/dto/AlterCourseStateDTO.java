package com.cqupt.knowtolearn.model.dto;

/**
 * @author Ray
 * @date 2023/7/29 15:40
 * @description
 */
public class AlterCourseStateDTO {

    /** 活动ID */
    private Integer courseId;

    /** 更新前状态 */
    private Integer beforeState;

    /** 更新后状态 */
    private Integer afterState;

    public AlterCourseStateDTO() {
    }

    public AlterCourseStateDTO(Integer courseId, Integer beforeState, Integer afterState) {
        this.courseId = courseId;
        this.beforeState = beforeState;
        this.afterState = afterState;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getBeforeState() {
        return beforeState;
    }

    public void setBeforeState(Integer beforeState) {
        this.beforeState = beforeState;
    }

    public Integer getAfterState() {
        return afterState;
    }

    public void setAfterState(Integer afterState) {
        this.afterState = afterState;
    }
}
