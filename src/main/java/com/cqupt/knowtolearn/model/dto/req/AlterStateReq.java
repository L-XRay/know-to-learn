package com.cqupt.knowtolearn.model.dto.req;

/**
 * @author Ray
 * @date 2023/7/29 18:50
 * @description
 */
public class AlterStateReq {

    private Integer courseId;

    private Integer currentState;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }
}
