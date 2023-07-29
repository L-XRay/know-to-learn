package com.cqupt.knowtolearn.service.course.stateflow.event;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.service.course.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * @author Ray
 * @date 2023/7/29 16:01
 * @description 发布状态
 */
@Component
public class PublishState extends AbstractState {

    @Override
    public Result arraignment(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("发布课程不可提审");
    }

    @Override
    public Result checkRevoke(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("发布课程不可撤审");
    }

    @Override
    public Result checkPass(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("发布课程不可审核通过");
    }

    @Override
    public Result checkRefuse(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("发布课程不可审核拒绝");
    }

    @Override
    public Result publish(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("发布课程不可重复发布");
    }
}
