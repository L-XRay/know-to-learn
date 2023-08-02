package com.cqupt.knowtolearn.dao.course;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.knowtolearn.model.dto.AlterCourseStateDTO;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.model.vo.CourseVO;
import com.cqupt.knowtolearn.model.vo.HomeCourseVO;
import com.cqupt.knowtolearn.model.vo.OrgCourseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Ray
 * @date 2023-07-19
 * @description 课程基本信息 dao 接口
 */
@Mapper
public interface ICourseBaseDao extends BaseMapper<CourseBase> {

    List<HomeCourseVO> randCourse();

    /**
     * 变更课程状态
     *
     * @param alterStateVo  [courseId、beforeState、afterState]
     * @return 更新数量
     */
    int alterState(AlterCourseStateDTO alterStateVo);

    List<OrgCourseVO> selectOrgCourse(Integer orgId);

    CourseVO selectCourseVoById(Integer orgId, Integer courseId);

    Integer selectCourseIsOwn(Integer orgId, Integer courseId);
}
