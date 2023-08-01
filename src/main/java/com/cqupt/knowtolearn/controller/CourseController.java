package com.cqupt.knowtolearn.controller;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.model.dto.req.AlterStateReq;
import com.cqupt.knowtolearn.model.dto.req.ChapterReq;
import com.cqupt.knowtolearn.model.dto.req.CourseReq;
import com.cqupt.knowtolearn.model.dto.req.MediaReq;
import com.cqupt.knowtolearn.model.dto.res.CosRes;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.model.vo.HomeCourseVO;
import com.cqupt.knowtolearn.service.chapter.ICourseDetailsService;
import com.cqupt.knowtolearn.service.chapter.stateflow.IMediaStateHandler;
import com.cqupt.knowtolearn.service.course.ICourseBaseService;
import com.cqupt.knowtolearn.service.system.impl.CosService;
import com.cqupt.knowtolearn.utils.UserHolder;
import com.qcloud.cos.http.HttpMethodName;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
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

//    @Resource
//    private IStateHandler stateHandler;

    @Resource
    private IMediaStateHandler mediaStateHandler;

    @Resource
    private CosService cosService;

    @Resource
    private ICourseDetailsService courseDetailsService;

    @GetMapping("/course/recommendation")
    public Result getHomeCourse(HttpServletRequest request) {
        List<HomeCourseVO> homeCourse = courseBaseService.getHomeCourse();
        if (homeCourse == null || homeCourse.size() == 0)  {
            return Result.fail("获取首页课程失败");
        }
        return Result.success("获取首页课程成功",homeCourse);
    }
//
//    @PostMapping("/org/course/audit/arraignment")
//    public Result arraignment(HttpServletRequest request, @RequestBody AlterStateReq req) {
//        Integer courseId = req.getCourseId();
//        Integer currentState = req.getCurrentState();
//        Constants.CourseState currentStateEum = getCurrentStateEnum(currentState);
//        return stateHandler.arraignment(courseId,currentStateEum);
//    }
//
//    @PostMapping("/admin/course/audit/checkPass")
//    public Result checkPass(HttpServletRequest request, @RequestBody AlterStateReq req) {
//        Integer courseId = req.getCourseId();
//        Integer currentState = req.getCurrentState();
//        Constants.CourseState currentStateEum = getCurrentStateEnum(currentState);
//        return stateHandler.checkPass(courseId,currentStateEum);
//    }
//
//    @PostMapping("/admin/course/audit/checkRefuse")
//    public Result checkRefuse(HttpServletRequest request, @RequestBody AlterStateReq req) {
//        Integer courseId = req.getCourseId();
//        Integer currentState = req.getCurrentState();
//        Constants.CourseState currentStateEum = getCurrentStateEnum(currentState);
//        return stateHandler.checkRefuse(courseId,currentStateEum);
//    }
//
//    @PostMapping("/org/course/audit/checkRevoke")
//    public Result checkRevoke(HttpServletRequest request, @RequestBody AlterStateReq req) {
//        Integer courseId = req.getCourseId();
//        Integer currentState = req.getCurrentState();
//        Constants.CourseState currentStateEum = getCurrentStateEnum(currentState);
//        return stateHandler.checkRevoke(courseId, currentStateEum);
//    }
//
//    @PostMapping("/org/course/audit/publish")
//    public Result publish(HttpServletRequest request, @RequestBody AlterStateReq req) {
//        Integer courseId = req.getCourseId();
//        Integer currentState = req.getCurrentState();
//        Constants.CourseState currentStateEum = getCurrentStateEnum(currentState);
//        return stateHandler.publish(courseId, currentStateEum);
//    }

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

    @PostMapping("/org/course/create/chapter")
    public Result createChapter(HttpServletRequest request, @RequestBody ChapterReq req) {
        courseDetailsService.addCourseChapter(req);
        return Result.success("创建课程章节成功",true);
    }

    @GetMapping("/org/course/{courseId}/chapter")
    public Result getChapter(HttpServletRequest request,@PathVariable("courseId") Integer courseId) {
        List<Map<String, Object>> data = courseDetailsService.getChapter(courseId);
        return Result.success("获取课程章节成功",data);
    }

    @PostMapping("/org/course/create/chapter/media")
    public Result createChapterMedia(HttpServletRequest request, @RequestBody MediaReq req) {
        URL url = courseDetailsService.addChapterMedia(req);
        return Result.success("创建章节视频信息成功",url);
    }

    @GetMapping("/org/course/chapter/{chapterId}/media")
    public Result getMedia(HttpServletRequest request, @PathVariable("chapterId") Integer chapterId) {
        List<Map<String, Object>> data = courseDetailsService.getMedia(chapterId);
        return Result.success("获取章节视频成功",data);
    }

    @PostMapping("/org/course/chapter/media/audit/arraignment")
    public Result arraignmentMedia(HttpServletRequest request, @RequestBody Map<String,Integer> req) {
        Integer mediaId = req.get("mediaId");
        Integer currentState = req.get("currentState");
        Constants.MediaState currentStateEum = getCurrentStateEnumM(currentState);
        return mediaStateHandler.arraignment(mediaId,currentStateEum);
    }

    @PostMapping("/org/course/chapter/media/audit/checkPass")
    public Result checkPassMedia(HttpServletRequest request, @RequestBody Map<String,Integer> req) {
        Integer mediaId = req.get("mediaId");
        Integer currentState = req.get("currentState");
        Constants.MediaState currentStateEum = getCurrentStateEnumM(currentState);
        return mediaStateHandler.checkPass(mediaId,currentStateEum);
    }

    @PostMapping("/org/course/chapter/media/audit/checkRefuse")
    public Result checkRefuseMedia(HttpServletRequest request, @RequestBody Map<String,Integer> req) {
        Integer mediaId = req.get("mediaId");
        Integer currentState = req.get("currentState");
        Constants.MediaState currentStateEum = getCurrentStateEnumM(currentState);
        return mediaStateHandler.checkRefuse(mediaId,currentStateEum);
    }

    @PostMapping("/org/course/chapter/media/audit/publish")
    public Result publishMedia(HttpServletRequest request, @RequestBody Map<String,Integer> req) {
        Integer mediaId = req.get("mediaId");
        Integer currentState = req.get("currentState");
        Constants.MediaState currentStateEum = getCurrentStateEnumM(currentState);
        return mediaStateHandler.publish(mediaId,currentStateEum);
    }

    @PostMapping("/org/course/chapter/media/audit/edit")
    public Result editMedia(HttpServletRequest request, @RequestBody Map<String,Integer> req) {
        Integer mediaId = req.get("mediaId");
        Integer currentState = req.get("currentState");
        Constants.MediaState currentStateEum = getCurrentStateEnumM(currentState);
        return mediaStateHandler.editing(mediaId,currentStateEum);
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

    private Constants.MediaState getCurrentStateEnumM(Integer beforeState) {
        Constants.MediaState currentState = null;
        for (Constants.MediaState state : Constants.MediaState.values()) {
            if (beforeState.equals(state.getCode())) {
                currentState = state;
            }
        }
        return currentState;
    }
}
