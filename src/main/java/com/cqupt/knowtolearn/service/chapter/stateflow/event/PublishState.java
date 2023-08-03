package com.cqupt.knowtolearn.service.chapter.stateflow.event;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.service.chapter.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * @author Ray
 * @date 2023/7/29 16:01
 * @description 发布状态
 */
@Component
public class PublishState extends AbstractState {

    @Override
    public Result editing(Integer mediaId, Enum<Constants.MediaState> currentState) {
        boolean isSuccess = courseDetailsService.alterStatus(mediaId, currentState, Constants.MediaState.EDIT);
        return isSuccess ? Result.success(200, "媒资变更编辑中成功") : Result.fail("媒资状态变更失败");
    }

    @Override
    public Result arraignment(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("发布媒资不可提审");
    }

    @Override
    public Result checkRevoke(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("发布媒资不可撤审");
    }

    @Override
    public Result checkPass(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("发布媒资不可审核通过");
    }

    @Override
    public Result checkRefuse(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("发布媒资不可审核拒绝");
    }

    @Override
    public Result publish(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("发布媒资不可重复发布");
    }

    @Override
    public Result publishRevoke(Integer mediaId, Enum<Constants.MediaState> currentState) {
        boolean isSuccess = courseDetailsService.alterStatus(mediaId, currentState, Constants.MediaState.PASS);
        return isSuccess ? Result.success(200,"媒资取消发布成功") : Result.fail("媒资状态变更失败");
    }
}
