package com.cqupt.knowtolearn.service.course.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.dao.course.ICourseBaseDao;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.model.vo.CourseVO;
import com.cqupt.knowtolearn.model.vo.HomeCourseVO;
import com.cqupt.knowtolearn.service.course.ICourseBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
* @author Ray
* @date 2023-07-19
* @description 课程基本信息 服务实现
*/
@Slf4j
@Service
public class CourseBaseServiceImpl extends ServiceImpl<ICourseBaseDao, CourseBase> implements ICourseBaseService {

    @Resource
    private ICourseBaseDao courseBaseDao;

    @Override
    public List<HomeCourseVO> getHomeCourse() {
        return courseBaseDao.randCourse();
    }
}
