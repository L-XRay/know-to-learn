package com.cqupt.knowtolearn.service.chapter.stateflow.event;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.service.chapter.stateflow.AbstractState;
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
    public Result editing(Integer mediaId, Enum<Constants.MediaState> currentState) {
        boolean isSuccess = courseDetailsService.alterStatus(mediaId, currentState, Constants.MediaState.EDIT);
        return isSuccess ? Result.success(200, "媒资变更编辑中成功") : Result.fail("媒资状态变更失败");
    }

    @Override
    public Result arraignment(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("已审核媒资不可重复提审");
    }

    @Override
    public Result checkRevoke(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("已审核媒资不可撤审");
    }

    @Override
    public Result checkPass(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("已审核媒资不可重复审核");
    }

    @Override
    public Result checkRefuse(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("已审核媒资不可重复审核");
    }

    @Override
    public Result publish(Integer mediaId, Enum<Constants.MediaState> currentState) {
        boolean isSuccess = courseDetailsService.alterStatus(mediaId, currentState, Constants.MediaState.PUBLISH);
        return isSuccess ? Result.success(200, "媒资发布成功") : Result.fail("媒资状态变更失败");
    }

    @Override
    public Result publishRevoke(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("已审核媒资不可取消发布");
    }
}
