package com.cqupt.knowtolearn.service.course.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.dao.course.ICourseBaseDao;
import com.cqupt.knowtolearn.dao.user.IUserDao;
import com.cqupt.knowtolearn.model.dto.AlterCourseStateDTO;
import com.cqupt.knowtolearn.model.dto.req.CourseReq;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.model.vo.HomeCourseVO;
import com.cqupt.knowtolearn.service.course.ICourseBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

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

    @Resource
    private IUserDao userDao;

    @Override
    public List<HomeCourseVO> getHomeCourse() {
        return courseBaseDao.randCourse();
    }

    @Override
    public boolean alterStatus(Integer courseId, Enum<Constants.CourseState> beforeState, Enum<Constants.CourseState> afterState) {
        AlterCourseStateDTO req = new AlterCourseStateDTO(courseId,((Constants.CourseState)beforeState).getCode(),((Constants.CourseState)afterState).getCode());
        int count = courseBaseDao.alterState(req);
        return 1 == count;
    }

    @Override
    public CourseBase selectOneById(Integer courseId) {
        return courseBaseDao.selectById(courseId);
    }

    @Override
    public CourseBase addCourse(Integer userId, CourseReq req) {
        User user = userDao.selectById(userId);
        Integer orgId = user.getOrgId();
        CourseBase courseBase = new CourseBase();
        courseBase.setName(req.getName());
        courseBase.setTeachers(req.getTeachers());
        courseBase.setTags(req.getTags());
        courseBase.setCategory(req.getCategory());
        courseBase.setDescription(req.getIntroduction());
        courseBase.setPic(req.getPic());
        courseBase.setOrgId(orgId);
        courseBase.setStatus(1);
        courseBase.setCreateDate(LocalDateTime.now());
        courseBase.setChangeDate(LocalDateTime.now());

        int insert = courseBaseDao.insert(courseBase);
        if (insert!=1) {
            throw new RuntimeException("创建课程失败");
        }

        return courseBase;
    }
}
