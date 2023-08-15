package com.cqupt.knowtolearn.controller;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.dao.user.IOrgDao;
import com.cqupt.knowtolearn.model.dto.CourseDetailDTO;
import com.cqupt.knowtolearn.model.dto.req.*;
import com.cqupt.knowtolearn.model.dto.res.CosRes;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.model.vo.CourseVO;
import com.cqupt.knowtolearn.model.vo.HomeCourseVO;
import com.cqupt.knowtolearn.model.vo.OrgCourseVO;
import com.cqupt.knowtolearn.model.vo.QueryOrgVO;
import com.cqupt.knowtolearn.service.chapter.ICourseDetailsService;
import com.cqupt.knowtolearn.service.chapter.stateflow.IMediaStateHandler;
import com.cqupt.knowtolearn.service.comment.ICommentService;
import com.cqupt.knowtolearn.service.course.ICourseBaseService;
import com.cqupt.knowtolearn.service.system.impl.CosService;
import com.cqupt.knowtolearn.service.user.IOrgService;
import com.cqupt.knowtolearn.utils.UserHolder;
import com.qcloud.cos.http.HttpMethodName;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.HashMap;
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

    @Resource
    private ICommentService commentService;

    @Resource
    private IOrgService orgService;

    //147
    @GetMapping("/course/recommendation")
    public Result getHomeCourse(HttpServletRequest request) {
        List<HomeCourseVO> homeCourse = courseBaseService.getHomeCourse();
        if (homeCourse == null || homeCourse.size() == 0)  {
            return Result.fail("获取首页课程失败");
        }
        return Result.success("获取首页课程成功",homeCourse);
    }
/**
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
*/

    //147
    @PostMapping("/org/course/create")
    public Result create(HttpServletRequest request, @RequestBody CourseReq req) {
        Map<String, Object> data = courseBaseService.addCourse(UserHolder.getUser(), req);
        return Result.success("创建课程成功",data);
    }

    @PostMapping("/org/course/pic")
    public Result getCoursePicSignature(HttpServletRequest request, @RequestBody Map<String,String> req) {
        String suffix = req.get("suffix");
        CosRes materialSignature = cosService.getOrgMaterialSignature(HttpMethodName.PUT, suffix);
        return Result.success("获取COS签名URL成功",materialSignature);
    }

    //147
    @PostMapping("/org/course/create/chapter")
    public Result createChapter(HttpServletRequest request, @RequestBody ChapterReq req) {
        Integer chapterId = courseDetailsService.addCourseChapter(req);
        return Result.success("创建课程章节成功",chapterId);
    }

    //147
    @GetMapping("/org/course/{courseId}/chapter")
    public Result getChapter(HttpServletRequest request,@PathVariable("courseId") Integer courseId) {
        List<Map<String, Object>> data = courseDetailsService.getChapter(courseId);
        return Result.success("获取课程章节成功",data);
    }

    //147
    @PostMapping("/org/course/create/chapter/media")
    public Result createChapterMedia(HttpServletRequest request, @RequestBody MediaReq req) {
        Map<String, Object> data = courseDetailsService.addChapterMedia(req);
        return Result.success("创建章节视频信息成功",data);
    }

    //147
    @GetMapping("/org/course/chapter/{chapterId}/media")
    public Result getMedia(HttpServletRequest request, @PathVariable("chapterId") Integer chapterId) {
        List<Map<String, Object>> data = courseDetailsService.getMedia(chapterId);
        return Result.success("获取章节视频成功",data);
    }


    @PostMapping("/org/course/chapter/media/update")
    public Result uploadMedia(HttpServletRequest request,@RequestBody Map<String,Object> req) {
        String chapterName = (String) req.get("name");
        String suffix = (String) req.get("suffix");
        Integer chapterId = (Integer) req.get("chapterId");
        if (suffix==null|| suffix.isEmpty()) {
            courseDetailsService.updateChapter(chapterId,chapterName);
            return Result.success(200,"修改章节视频成功");
        }
        CosRes cosRes = cosService.getCourseMediaSignature(HttpMethodName.PUT,chapterId,chapterName, suffix);
        return Result.success("修改章节视频成功",cosRes);
    }


    @PostMapping("/org/course/chapter/media/audit/arraignment")
    public Result arraignmentMedia(HttpServletRequest request, @RequestBody Map<String,Integer> req) {
        Integer mediaId = req.get("mediaId");
        Integer currentState = req.get("currentState");
        Constants.MediaState currentStateEum = getCurrentStateEnumM(currentState);
        return mediaStateHandler.arraignment(mediaId,currentStateEum);
    }

    @PostMapping("/admin/course/chapter/media/audit/checkPass")
    public Result checkPassMedia(HttpServletRequest request, @RequestBody Map<String,Integer> req) {
        Integer mediaId = req.get("mediaId");
        Integer currentState = req.get("currentState");
        Constants.MediaState currentStateEum = getCurrentStateEnumM(currentState);
        return mediaStateHandler.checkPass(mediaId,currentStateEum);
    }

    @PostMapping("/admin/course/chapter/media/audit/checkRefuse")
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

    @PostMapping("/org/course/chapter/media/audit/checkRevoke")
    public Result checkRevokeMedia(HttpServletRequest request, @RequestBody Map<String,Integer> req) {
        Integer mediaId = req.get("mediaId");
        Integer currentState = req.get("currentState");
        Constants.MediaState currentStateEum = getCurrentStateEnumM(currentState);
        return mediaStateHandler.checkRevoke(mediaId,currentStateEum);
    }

    @PostMapping("/org/course/chapter/media/audit/publishRevoke")
    public Result publishRevokeMedia(HttpServletRequest request, @RequestBody Map<String,Integer> req) {
        Integer mediaId = req.get("mediaId");
        Integer currentState = req.get("currentState");
        Constants.MediaState currentStateEum = getCurrentStateEnumM(currentState);
        return mediaStateHandler.publishRevoke(mediaId,currentStateEum);
    }

    //147
    @GetMapping("/org/course/all")
    public Result getOwnOrgCourse(HttpServletRequest request) {
        List<OrgCourseVO> course = courseBaseService.getOwnCourse(UserHolder.getUser());
        return Result.success("获取本机构课程成功",course);
    }

    //147
    @GetMapping("/user/course/{courseId}/get")
    public Result getCourse(HttpServletRequest request, @PathVariable("courseId") Integer courseId) {
        CourseVO courseVO = courseBaseService.selectCourseVoById(UserHolder.getUser(), courseId);
        return Result.success("查询指定课程基本信息成功",courseVO);
    }

    @GetMapping("/user/course/{courseId}/details")
    public Result getCourseDetails(HttpServletRequest request,@PathVariable("courseId") Integer courseId) {
        Map<String,Object> data = courseDetailsService.getCourseDetail(UserHolder.getUser(), courseId);
        return Result.success("查询课程详细信息成功",data);
    }

    @GetMapping("/user/course/chapter/{chapterId}/details")
    public Result getChapterDetails(HttpServletRequest request,@PathVariable("chapterId") Integer chapterId) {
        Map<String, Object> data = courseDetailsService.getSimpleCourseDetail(chapterId);
        if (data==null) {
            return Result.fail(400,"该视频尚未发布");
        }
        return Result.success("查询视频页信息成功",data);
    }

    // TODO 无法保证是自己机构才能删除
    //147
    @DeleteMapping("/org/course/{courseId}/delete")
    public Result deleteCourse(HttpServletRequest request,@PathVariable("courseId") Integer courseId) {
        courseBaseService.deleteCourse(courseId);
        return Result.success("删除课程成功",true);
    }

    //147
    @PostMapping("/org/course/{courseId}/update")
    public Result updateCourse(HttpServletRequest request,@PathVariable("courseId") Integer courseId, @RequestBody CourseReq req) {
        Map<String, Object> data = courseBaseService.updateCourse(UserHolder.getUser(), courseId, req);
        return Result.success("修改课程成功",data);
    }

    //147
    @DeleteMapping("/org/chapter/{chapterId}/delete")
    public Result deleteChapter(HttpServletRequest request,@PathVariable("chapterId") Integer chapterId) {
        courseDetailsService.deleteChapter(chapterId);
        return Result.success("删除章节成功",true);
    }

    // TODO 无法保证是自己机构才能修改
    //147
    @PostMapping("/org/chapter/update")
    public Result updateChapter(HttpServletRequest request,@RequestBody Map<String,Object> req) {
        Integer chapterId = (Integer) req.get("chapterId");
        String chapterName = (String) req.get("chapterName");
        courseDetailsService.updateChapter(chapterId,chapterName);
        return Result.success("修改章节成功",true);
    }

    @PostMapping("/user/comment/add")
    public Result deleteCourse(HttpServletRequest request, @RequestBody CommentReq req) {
        commentService.addComment(UserHolder.getUser(),req);
        return Result.success("评论成功",true);
    }

    //147
    @GetMapping("/search")
    public Result searchAll(HttpServletRequest request, @RequestParam String key) {
        List<QueryOrgVO> orgList = orgService.selectOrgList(key);
        List<HomeCourseVO> courseList = courseBaseService.selectCourseList(key);
        Map<String,Object> data = new HashMap<>();
        data.put("courses",courseList);
        data.put("orgs",orgList);
        return Result.success("获取查询结果成功",data);
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
