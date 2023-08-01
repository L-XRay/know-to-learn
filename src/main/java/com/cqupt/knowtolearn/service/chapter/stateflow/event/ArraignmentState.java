package com.cqupt.knowtolearn.service.chapter.stateflow.event;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.service.chapter.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * @author Ray
 * @date 2023/7/29 15:51
 * @description 提审状态
 */
@Component
public class ArraignmentState extends AbstractState {

    @Override
    public Result editing(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("待审核状态不可编辑");
    }

    @Override
    public Result arraignment(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("待审核状态不可重复提审");
    }

    @Override
    public Result checkPass(Integer mediaId, Enum<Constants.MediaState> currentState) {
        boolean isSuccess = courseDetailsService.alterStatus(mediaId, currentState, Constants.MediaState.PASS);
        return isSuccess ? Result.success(200, "媒资审核通过完成") : Result.fail("媒资状态变更失败");
    }

    @Override
    public Result checkRefuse(Integer mediaId, Enum<Constants.MediaState> currentState) {
        boolean isSuccess = courseDetailsService.alterStatus(mediaId, currentState, Constants.MediaState.REFUSE);
        return isSuccess ? Result.success(200, "媒资审核拒绝完成") : Result.fail("媒资状态变更失败");
    }

    @Override
    public Result publish(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("待审核媒资不可发布");
    }
}
