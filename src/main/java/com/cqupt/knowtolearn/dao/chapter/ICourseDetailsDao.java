package com.cqupt.knowtolearn.dao.chapter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.knowtolearn.model.dto.AlterCourseStateDTO;
import com.cqupt.knowtolearn.model.dto.AlterMediaStateDTO;
import com.cqupt.knowtolearn.model.dto.CourseDetailDTO;
import com.cqupt.knowtolearn.model.dto.SimpleCourseDetailDTO;
import com.cqupt.knowtolearn.model.po.chapter.CourseDetails;
import com.cqupt.knowtolearn.model.vo.CourseDetailVO;
import com.cqupt.knowtolearn.model.vo.OrgVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Ray
 * @date 2023/8/1 10:14
 * @description
 */
@Mapper
public interface ICourseDetailsDao extends BaseMapper<CourseDetails> {

    /**
     * 变更媒资状态
     *
     * @param alterStateVo  [mediaId、beforeState、afterState]
     * @return 更新数量
     */
    int alterState(AlterMediaStateDTO alterStateVo);

    List<CourseDetailDTO> selectTreeNodes(Integer courseId);

    List<CourseDetailVO> selectPendingMediaList();

    List<SimpleCourseDetailDTO> selectSimpleTreeNodes(Integer courseId);

}
