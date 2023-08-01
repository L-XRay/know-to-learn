//package com.cqupt.knowtolearn.service.course.stateflow.event;
//
//import com.cqupt.knowtolearn.common.Constants;
//import com.cqupt.knowtolearn.common.Result;
//import com.cqupt.knowtolearn.service.course.stateflow.AbstractState;
//import org.springframework.stereotype.Component;
//
///**
// * @author Ray
// * @date 2023/7/29 15:51
// * @description 提审状态
// */
//@Component
//public class ArraignmentState extends AbstractState {
//
//    @Override
//    public Result arraignment(Integer courseId, Enum<Constants.CourseState> currentState) {
//        return Result.fail("待审核状态不可重复提审");
//    }
//
//    @Override
//    public Result checkRevoke(Integer courseId, Enum<Constants.CourseState> currentState) {
//        boolean isSuccess = courseBaseService.alterStatus(courseId, currentState, Constants.CourseState.EDIT);
//        return isSuccess ? Result.success(200,"课程审核撤销回到编辑状态") : Result.fail("课程状态变更失败");
//    }
//
//    @Override
//    public Result checkPass(Integer courseId, Enum<Constants.CourseState> currentState) {
//        boolean isSuccess = courseBaseService.alterStatus(courseId, currentState, Constants.CourseState.PASS);
//        return isSuccess ? Result.success(200, "课程审核通过完成") : Result.fail("课程状态变更失败");
//    }
//
//    @Override
//    public Result checkRefuse(Integer courseId, Enum<Constants.CourseState> currentState) {
//        boolean isSuccess = courseBaseService.alterStatus(courseId, currentState, Constants.CourseState.REFUSE);
//        return isSuccess ? Result.success(200, "课程审核拒绝完成") : Result.fail("课程状态变更失败");
//    }
//
//    @Override
//    public Result publish(Integer courseId, Enum<Constants.CourseState> currentState) {
//        return Result.fail("待审核课程不可发布");
//    }
//}
