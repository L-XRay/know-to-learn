//package com.cqupt.knowtolearn.service.course.stateflow;
//
//import com.cqupt.knowtolearn.common.Constants;
//import com.cqupt.knowtolearn.common.Result;
//import com.cqupt.knowtolearn.dao.course.ICourseBaseDao;
//import com.cqupt.knowtolearn.service.course.ICourseBaseService;
//
//import javax.annotation.Resource;
//
///**
// * @author Ray
// * @date 2023/7/29 15:32
// * @description 活动状态抽象类
// */
//public abstract class AbstractState {
//
//    @Resource
//    protected ICourseBaseService courseBaseService;
//
//    /**
//     * 课程提审
//     * @param courseId 课程ID
//     * @param currentState 当前状态
//     * @return 执行结果
//     */
//    public abstract Result arraignment(Integer courseId, Enum<Constants.CourseState> currentState);
//
//    /**
//     * 课程撤审
//     * @param courseId 课程ID
//     * @param currentState 当前状态
//     * @return 执行结果
//     */
//    public abstract Result checkRevoke(Integer courseId, Enum<Constants.CourseState> currentState);
//
//    /**
//     * 审核通过
//     * @param courseId 课程ID
//     * @param currentState 当前状态
//     * @return 执行结果
//     */
//    public abstract Result checkPass(Integer courseId, Enum<Constants.CourseState> currentState);
//
//    /**
//     * 审核拒绝
//     * @param courseId 课程ID
//     * @param currentState 当前状态
//     * @return 执行结果
//     */
//    public abstract Result checkRefuse(Integer courseId, Enum<Constants.CourseState> currentState);
//
//    /**
//     * 课程发布
//     * @param courseId 课程ID
//     * @param currentState 当前状态
//     * @return 执行结果
//     */
//    public abstract Result publish(Integer courseId, Enum<Constants.CourseState> currentState);
//}
