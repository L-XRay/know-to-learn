package com.cqupt.knowtolearn.service.chapter.stateflow;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;

/**
 * @author Ray
 * @date 2023/7/29 15:15
 * @description 状态处理接口
 */
public interface IMediaStateHandler {

    /**
     * 媒资编辑中
     * @param mediaId       媒资ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result editing(Integer mediaId, Enum<Constants.MediaState> currentStatus);

    /**
     * 提交审核
     * @param mediaId       媒资ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result arraignment(Integer mediaId, Enum<Constants.MediaState> currentStatus);

    /**
     * 撤回审核
     * @param mediaId       媒资ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result checkRevoke(Integer mediaId, Enum<Constants.MediaState> currentStatus);

    /**
     * 审核通过
     * @param mediaId       媒资ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result checkPass(Integer mediaId, Enum<Constants.MediaState> currentStatus);

    /**
     * 审核拒绝
     * @param mediaId       媒资ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result checkRefuse(Integer mediaId, Enum<Constants.MediaState> currentStatus);

    /**
     * 发布
     * @param mediaId       媒资ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result publish(Integer mediaId, Enum<Constants.MediaState> currentStatus);

    /**
     * 取消发布
     * @param mediaId       媒资ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result publishRevoke(Integer mediaId, Enum<Constants.MediaState> currentStatus);

}
