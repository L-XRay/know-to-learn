package com.cqupt.knowtolearn.service.chapter.stateflow;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.dao.chapter.ICourseDetailsDao;
import com.cqupt.knowtolearn.dao.user.IUserDao;
import com.cqupt.knowtolearn.service.chapter.ICourseDetailsService;
import com.cqupt.knowtolearn.service.course.ICourseBaseService;
import com.cqupt.knowtolearn.service.system.IStationMessageService;
import com.cqupt.knowtolearn.service.user.IUserService;

import javax.annotation.Resource;

/**
 * @author Ray
 * @date 2023/7/29 15:32
 * @description 活动状态抽象类
 */
public abstract class AbstractState {

    @Resource
    protected ICourseDetailsService courseDetailsService;

    @Resource
    protected IStationMessageService stationMessageService;

    @Resource
    protected ICourseBaseService courseBaseService;

    @Resource
    protected IUserService userService;

    /**
     * 媒资编辑中
     * @param mediaId      媒资ID
     * @param currentState 当前状态
     * @return 执行结果
     */
    public abstract Result editing(Integer mediaId, Enum<Constants.MediaState> currentState);

    /**
     * 媒资提审
     * @param mediaId      媒资ID
     * @param currentState 当前状态
     * @return 执行结果
     */
    public abstract Result arraignment(Integer mediaId, Enum<Constants.MediaState> currentState);

    /**
     * 媒资撤审
     * @param mediaId      媒资ID
     * @param currentState 当前状态
     * @return 执行结果
     */
    public abstract Result checkRevoke(Integer mediaId, Enum<Constants.MediaState> currentState);

    /**
     * 审核通过
     * @param mediaId      媒资ID
     * @param currentState 当前状态
     * @return 执行结果
     */
    public abstract Result checkPass(Integer mediaId, Enum<Constants.MediaState> currentState);

    /**
     * 审核拒绝
     * @param mediaId      媒资ID
     * @param currentState 当前状态
     * @return 执行结果
     */
    public abstract Result checkRefuse(Integer mediaId, Enum<Constants.MediaState> currentState);

    /**
     * 媒资发布
     * @param mediaId      媒资ID
     * @param currentState 当前状态
     * @return 执行结果
     */
    public abstract Result publish(Integer mediaId, Enum<Constants.MediaState> currentState);

    /**
     * 取消发布
     * @param mediaId      媒资ID
     * @param currentState 当前状态
     * @return 执行结果
     */
    public abstract Result publishRevoke(Integer mediaId, Enum<Constants.MediaState> currentState);
}
