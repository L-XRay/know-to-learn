package com.cqupt.knowtolearn.service.chapter.stateflow.impl;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.service.chapter.stateflow.IMediaStateHandler;
import com.cqupt.knowtolearn.service.chapter.stateflow.StateConfig;
import org.springframework.stereotype.Service;

/**
 * @author Ray
 * @date 2023/7/29 16:37
 * @description 状态处理服务
 */
@Service
public class MediaStateHandler extends StateConfig implements IMediaStateHandler {

    @Override
    public Result editing(Integer mediaId, Enum<Constants.MediaState> currentStatus) {
        return stateGroup.get(currentStatus).editing(mediaId, currentStatus);
    }

    @Override
    public Result arraignment(Integer mediaId, Enum<Constants.MediaState> currentStatus) {
        return stateGroup.get(currentStatus).arraignment(mediaId,currentStatus);
    }

    @Override
    public Result checkRevoke(Integer mediaId, Enum<Constants.MediaState> currentStatus) {
        return stateGroup.get(currentStatus).checkRevoke(mediaId,currentStatus);
    }

    @Override
    public Result checkPass(Integer mediaId, Enum<Constants.MediaState> currentStatus) {
        return stateGroup.get(currentStatus).checkPass(mediaId,currentStatus);
    }

    @Override
    public Result checkRefuse(Integer mediaId, Enum<Constants.MediaState> currentStatus) {
        return stateGroup.get(currentStatus).checkRefuse(mediaId, currentStatus);
    }

    @Override
    public Result publish(Integer mediaId, Enum<Constants.MediaState> currentStatus) {
        return stateGroup.get(currentStatus).publish(mediaId, currentStatus);
    }

    @Override
    public Result publishRevoke(Integer mediaId, Enum<Constants.MediaState> currentStatus) {
        return stateGroup.get(currentStatus).publishRevoke(mediaId, currentStatus);
    }
}
