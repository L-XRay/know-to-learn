//package com.cqupt.knowtolearn.service.course.stateflow;
//
//import com.cqupt.knowtolearn.common.Constants;
//import com.cqupt.knowtolearn.common.Result;
//
///**
// * @author Ray
// * @date 2023/7/29 15:15
// * @description 状态处理接口
// */
//public interface IStateHandler {
//
//    /**
//     * 提审
//     * @param courseId      课程ID
//     * @param currentStatus 当前状态
//     * @return              审核结果
//     */
//    Result arraignment(Integer courseId, Enum<Constants.CourseState> currentStatus);
//
//    /**
//     * 审核通过
//     * @param courseId      课程ID
//     * @param currentStatus 当前状态
//     * @return              审核结果
//     */
//    Result checkPass(Integer courseId, Enum<Constants.CourseState> currentStatus);
//
//    /**
//     * 审核拒绝
//     * @param courseId      课程ID
//     * @param currentStatus 当前状态
//     * @return              审核结果
//     */
//    Result checkRefuse(Integer courseId, Enum<Constants.CourseState> currentStatus);
//
//    /**
//     * 撤销审核
//     * @param courseId      课程ID
//     * @param currentStatus 当前状态
//     * @return              审核结果
//     */
//    Result checkRevoke(Integer courseId, Enum<Constants.CourseState> currentStatus);
//
//    /**
//     * 发布
//     * @param courseId      课程ID
//     * @param currentStatus 当前状态
//     * @return              审核结果
//     */
//    Result publish(Integer courseId, Enum<Constants.CourseState> currentStatus);
//
//}
