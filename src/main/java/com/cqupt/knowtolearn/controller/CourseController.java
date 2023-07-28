package com.cqupt.knowtolearn.controller;

import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.model.vo.CourseVO;
import com.cqupt.knowtolearn.model.vo.HomeCourseVO;
import com.cqupt.knowtolearn.service.course.ICourseBaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Ray
 * @date 2023/7/28 22:36
 * @description
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private ICourseBaseService courseBaseService;

    @GetMapping("/recommendation")
    public Result getHomeCourse(HttpServletRequest request) {
        List<HomeCourseVO> homeCourse = courseBaseService.getHomeCourse();
        if (homeCourse == null || homeCourse.size() == 0)  {
            return Result.fail("获取首页课程失败");
        }
        return Result.success("获取首页课程成功",homeCourse);
    }

}
