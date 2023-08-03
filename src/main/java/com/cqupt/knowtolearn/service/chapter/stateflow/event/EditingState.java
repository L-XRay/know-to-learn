package com.cqupt.knowtolearn.service.chapter.stateflow.event;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.service.chapter.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * @author Ray
 * @date 2023/7/29 16:06
 * @description
 */
@Component
public class EditingState extends AbstractState {

    @Override
    public Result editing(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("媒资编辑中");
    }

    @Override
    public Result checkRevoke(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("媒资编辑中不可撤审");
   }

    @Override
    public Result arraignment(Integer mediaId, Enum<Constants.MediaState> currentState) {
        boolean isSuccess = courseDetailsService.alterStatus(mediaId, currentState, Constants.MediaState.ARRAIGNMENT);
        return isSuccess ? Result.success(200,"媒资提审成功") : Result.fail("媒资状态变更失败");
    }

    @Override
    public Result checkPass(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("媒资编辑中不可审核通过");
    }

    @Override
    public Result checkRefuse(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("媒资编辑中不可拒绝");
    }

    @Override
    public Result publish(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("媒资编辑中不可发布");
    }

    @Override
    public Result publishRevoke(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("编辑中媒资不可取消发布");
    }
}
