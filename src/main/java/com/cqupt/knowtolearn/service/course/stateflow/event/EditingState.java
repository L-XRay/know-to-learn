package com.cqupt.knowtolearn.service.course.stateflow.event;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.service.course.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * @author Ray
 * @date 2023/7/29 16:06
 * @description
 */
@Component
public class EditingState extends AbstractState {

    @Override
    public Result arraignment(Integer courseId, Enum<Constants.CourseState> currentState) {
        boolean isSuccess = courseBaseService.alterStatus(courseId, currentState, Constants.CourseState.ARRAIGNMENT);
        return isSuccess ? Result.success(200,"课程提审成功") : Result.fail("课程状态变更失败");
    }

    @Override
    public Result checkRevoke(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("课程编辑中不可撤审");
    }

    @Override
    public Result checkPass(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("课程编辑中不可审核通过");
    }

    @Override
    public Result checkRefuse(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("课程编辑中不可拒绝");
    }

    @Override
    public Result publish(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("课程编辑中不可发布");
    }
}
