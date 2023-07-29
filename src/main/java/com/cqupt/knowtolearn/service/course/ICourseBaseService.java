package com.cqupt.knowtolearn.service.course;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.model.vo.CourseVO;
import com.cqupt.knowtolearn.model.vo.HomeCourseVO;

import java.util.List;

/**
* @author Ray
* @date 2023-07-19
* @description 课程基本信息 服务接口
*/
public interface ICourseBaseService extends IService<CourseBase> {

    List<HomeCourseVO> getHomeCourse();

    boolean alterStatus(Integer courseId, Enum<Constants.CourseState> beforeState, Enum<Constants.CourseState> afterState);

    CourseBase selectOneById(Integer courseId);
}