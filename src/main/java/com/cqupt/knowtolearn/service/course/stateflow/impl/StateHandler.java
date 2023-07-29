package com.cqupt.knowtolearn.service.course.stateflow.impl;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.service.course.stateflow.IStateHandler;
import com.cqupt.knowtolearn.service.course.stateflow.StateConfig;
import org.springframework.stereotype.Service;

/**
 * @author Ray
 * @date 2023/7/29 16:37
 * @description 状态处理服务
 */
@Service
public class StateHandler extends StateConfig implements IStateHandler {

    @Override
    public Result arraignment(Integer courseId, Enum<Constants.CourseState> currentStatus) {
        return stateGroup.get(currentStatus).arraignment(courseId,currentStatus);
    }

    @Override
    public Result checkPass(Integer courseId, Enum<Constants.CourseState> currentStatus) {
        return stateGroup.get(currentStatus).checkPass(courseId,currentStatus);
    }

    @Override
    public Result checkRefuse(Integer courseId, Enum<Constants.CourseState> currentStatus) {
        return stateGroup.get(currentStatus).checkRefuse(courseId,currentStatus);
    }

    @Override
    public Result checkRevoke(Integer courseId, Enum<Constants.CourseState> currentStatus) {
        return stateGroup.get(currentStatus).checkRevoke(courseId,currentStatus);
    }

    @Override
    public Result publish(Integer courseId, Enum<Constants.CourseState> currentStatus) {
        return stateGroup.get(currentStatus).publish(courseId,currentStatus);
    }
}
