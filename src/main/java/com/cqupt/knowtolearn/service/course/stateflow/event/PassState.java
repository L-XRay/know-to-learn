package com.cqupt.knowtolearn.service.course.stateflow.event;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.service.course.stateflow.AbstractState;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Ray
 * @date 2023/7/29 16:09
 * @description 课程审核通过状态
 */
@Component
public class PassState extends AbstractState {

    @Override
    public Result arraignment(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("已审核课程不可重复提审");
    }

    @Override
    public Result checkRevoke(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("审核通过课程不可撤审");
    }

    @Override
    public Result checkPass(Integer courseId, Enum<Constants.CourseState> currentState) {
        return Result.fail("已审核课程不可重复审核");
    }

    @Override
    public Result checkRefuse(Integer courseId, Enum<Constants.CourseState> currentState) {
        boolean isSuccess = courseBaseService.alterStatus(courseId, currentState, Constants.CourseState.REFUSE);
        return isSuccess ? Result.success(200,"课程审核拒绝成功") : Result.fail("课程状态变更失败");
    }

    @Override
    public Result publish(Integer courseId, Enum<Constants.CourseState> currentState) {
        boolean isSuccess = courseBaseService.alterStatus(courseId, currentState, Constants.CourseState.PUBLISH);
        if (isSuccess) {
            CourseBase courseBase = courseBaseService.selectOneById(courseId);
            courseBase.setPublishDate(LocalDateTime.now());
            courseBaseService.saveOrUpdate(courseBase);
            return Result.success(200, "课程发布成功");
        }
        return Result.fail("课程状态变更失败");
    }
}
