package com.cqupt.knowtolearn.service.course.stateflow.event;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.service.course.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * @author Ray
 * @date 2023/7/29 16:19
 * @description
 */
@Component
public class RefuseState extends AbstractState {
    @Override
    public Result arraignment(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("已审核课程不可重复提审");
    }

    @Override
    public Result checkRevoke(Integer courseId, Enum<Constants.CourseState> currentState) {
        boolean isSuccess = courseBaseService.alterStatus(courseId, currentState, Constants.CourseState.EDIT);
        return isSuccess ? Result.success(200,"课程撤审成功") : Result.fail("课程状态变更失败");
    }

    @Override
    public Result checkPass(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("已审核课程不可重复审核");
    }

    @Override
    public Result checkRefuse(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("课程审核拒绝不可重复审核");
    }

    @Override
    public Result publish(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("课程审核拒绝不可发布");
    }
}
