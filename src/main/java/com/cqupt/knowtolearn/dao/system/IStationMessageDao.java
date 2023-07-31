package com.cqupt.knowtolearn.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.model.po.system.StationMessage;
import com.cqupt.knowtolearn.model.vo.StationMessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Ray
 * @date 2023/7/31 10:06
 * @description 站内信 Dao 接口
 */
@Mapper
public interface IStationMessageDao extends BaseMapper<StationMessage> {

    List<StationMessageVO> selectMessage(Integer userId);

    void updateStationMessageRead(Integer userId);
}
