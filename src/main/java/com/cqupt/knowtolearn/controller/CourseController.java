package com.cqupt.knowtolearn.controller;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.model.dto.req.AlterStateReq;
import com.cqupt.knowtolearn.model.dto.req.CourseReq;
import com.cqupt.knowtolearn.model.dto.res.CosRes;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.model.vo.HomeCourseVO;
import com.cqupt.knowtolearn.service.course.ICourseBaseService;
import com.cqupt.knowtolearn.service.course.stateflow.IStateHandler;
import com.cqupt.knowtolearn.service.system.impl.CosService;
import com.cqupt.knowtolearn.utils.UserHolder;
import com.qcloud.cos.http.HttpMethodName;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Ray
 * @date 2023/7/28 22:36
 * @description
 */
@RestController
public class CourseController {

    @Resource
    private ICourseBaseService courseBaseService;

    @Resource
    private IStateHandler stateHandler;

    @Resource
    private CosService cosService;

    @GetMapping("/course/recommendation")
    public Result getHomeCourse(HttpServletRequest request) {
        List<HomeCourseVO> homeCourse = courseBaseService.getHomeCourse();
        if (homeCourse == null || homeCourse.size() == 0)  {
            return Result.fail("获取首页课程失败");
        }
        return Result.success("获取首页课程成功",homeCourse);
    }

    @PostMapping("/org/course/audit/arraignment")
    public Result arraignment(HttpServletRequest request, @RequestBody AlterStateReq req) {
        Integer courseId = req.getCourseId();
        Integer currentState = req.getCurrentState();
        Constants.CourseState currentStateEum = getCurrentStateEnum(currentState);
        return stateHandler.arraignment(courseId,currentStateEum);
    }

    @PostMapping("/admin/course/audit/checkPass")
    public Result checkPass(HttpServletRequest request, @RequestBody AlterStateReq req) {
        Integer courseId = req.getCourseId();
        Integer currentState = req.getCurrentState();
        Constants.CourseState currentStateEum = getCurrentStateEnum(currentState);
        return stateHandler.checkPass(courseId,currentStateEum);
    }

    @PostMapping("/admin/course/audit/checkRefuse")
    public Result checkRefuse(HttpServletRequest request, @RequestBody AlterStateReq req) {
        Integer courseId = req.getCourseId();
        Integer currentState = req.getCurrentState();
        Constants.CourseState currentStateEum = getCurrentStateEnum(currentState);
        return stateHandler.checkRefuse(courseId,currentStateEum);
    }

    @PostMapping("/org/course/audit/checkRevoke")
    public Result checkRevoke(HttpServletRequest request, @RequestBody AlterStateReq req) {
        Integer courseId = req.getCourseId();
        Integer currentState = req.getCurrentState();
        Constants.CourseState currentStateEum = getCurrentStateEnum(currentState);
        return stateHandler.checkRevoke(courseId, currentStateEum);
    }

    @PostMapping("/org/course/audit/publish")
    public Result publish(HttpServletRequest request, @RequestBody AlterStateReq req) {
        Integer courseId = req.getCourseId();
        Integer currentState = req.getCurrentState();
        Constants.CourseState currentStateEum = getCurrentStateEnum(currentState);
        return stateHandler.publish(courseId, currentStateEum);
    }

    @PostMapping("/org/course/create")
    public Result create(HttpServletRequest request, @RequestBody CourseReq req) {
        CourseBase courseBase = courseBaseService.addCourse(UserHolder.getUser(), req);
        return Result.success("创建课程成功",courseBase);
    }

    @PostMapping("/org/course/pic")
    public Result getCoursePicSignature(HttpServletRequest request, @RequestBody Map<String,String> req) {
        String suffix = req.get("suffix");
        CosRes materialSignature = cosService.getOrgMaterialSignature(HttpMethodName.PUT, suffix);
        return Result.success("获取COS签名URL成功",materialSignature);
    }

    private Constants.CourseState getCurrentStateEnum(Integer beforeState) {
        Constants.CourseState currentState = null;
        for (Constants.CourseState state : Constants.CourseState.values()) {
            if (beforeState.equals(state.getCode())) {
                currentState = state;
            }
        }
        return currentState;
    }
}
