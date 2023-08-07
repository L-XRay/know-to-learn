package com.cqupt.knowtolearn.service.course.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.dao.chapter.ICourseDetailsDao;
import com.cqupt.knowtolearn.dao.course.ICourseBaseDao;
import com.cqupt.knowtolearn.dao.user.IUserDao;
import com.cqupt.knowtolearn.model.dto.AlterCourseStateDTO;
import com.cqupt.knowtolearn.model.dto.req.CourseReq;
import com.cqupt.knowtolearn.model.po.chapter.CourseDetails;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.model.vo.CourseVO;
import com.cqupt.knowtolearn.model.vo.HomeCourseVO;
import com.cqupt.knowtolearn.model.vo.OrgCourseVO;
import com.cqupt.knowtolearn.service.course.ICourseBaseService;
import com.cqupt.knowtolearn.service.system.impl.CosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private ICourseDetailsDao courseDetailsDao;

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
    public Map<String, Object> addCourse(Integer userId, CourseReq req) {
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
        courseBase.setStatus(6);
        courseBase.setCreateDate(LocalDateTime.now());
        courseBase.setChangeDate(LocalDateTime.now());
        courseBase.setPublishDate(LocalDateTime.now());

        int insert = courseBaseDao.insert(courseBase);
        if (insert!=1) {
            throw new RuntimeException("创建课程失败");
        }

        ZoneId beijingZoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime zonedDateTime = courseBase.getPublishDate().atZone(beijingZoneId);
        long publishTime = zonedDateTime.toInstant().toEpochMilli();
        Map<String,Object> map = new HashMap<>();
        map.put("courseId",courseBase.getId());
        map.put("courseName",courseBase.getName());
        map.put("coverUrl",courseBase.getPic());
        map.put("publishTime",publishTime);

        return map;
    }

    @Override
    public List<OrgCourseVO> getOwnCourse(Integer userId) {
        User user = userDao.selectById(userId);
        Integer orgId = user.getOrgId();
        return courseBaseDao.selectOrgCourse(orgId);
    }

    @Override
    @Transactional
    public void deleteCourse(Integer courseId) {
        boolean b = this.removeById(courseId);
        if (!b) {
            throw new RuntimeException("课程基本信息删除失败");
        }
        List<CourseDetails> courseDetails = courseDetailsDao.selectList(new LambdaQueryWrapper<CourseDetails>().eq(CourseDetails::getCourseId, courseId));
        if (courseDetails==null||courseDetails.size()==0) {
            return;
        }
        int delete = courseDetailsDao.delete(new LambdaQueryWrapper<CourseDetails>().eq(CourseDetails::getCourseId, courseId));
        if (delete==0) {
            throw new RuntimeException("课程详细信息删除失败");
        }
    }

    @Override
    public CourseVO selectCourseVoById(Integer userId, Integer courseId) {
        User user = userDao.selectById(userId);
        Integer orgId = user.getOrgId();
        return courseBaseDao.selectCourseVoById(orgId, courseId);
    }

    @Override
    public Map<String, Object> updateCourse(Integer userId,Integer courseId, CourseReq req) {
        User user = userDao.selectById(userId);
        Integer orgId = user.getOrgId();
        CourseBase courseBase = courseBaseDao.selectById(courseId);
        if (!orgId.equals(courseBase.getOrgId())) {
            throw new RuntimeException("您无权修改该课程");
        }
        courseBase.setName(req.getName());
        courseBase.setTeachers(req.getTeachers());
        courseBase.setTags(req.getTags());
        courseBase.setCategory(req.getCategory());
        courseBase.setDescription(req.getIntroduction());
        courseBase.setChangeDate(LocalDateTime.now());
        courseBase.setPublishDate(LocalDateTime.now());
        courseBase.setPic(req.getPic());
        int update = courseBaseDao.updateById(courseBase);
        if (update!=1) {
            throw new RuntimeException("修改课程失败");
        }

        ZoneId beijingZoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime zonedDateTime = courseBase.getPublishDate().atZone(beijingZoneId);
        long publishTime = zonedDateTime.toInstant().toEpochMilli();
        Map<String,Object> map = new HashMap<>();
        map.put("courseId",courseBase.getId());
        map.put("courseName",courseBase.getName());
        map.put("coverUrl",courseBase.getPic());
        map.put("publishTime",publishTime);

        return map;
    }

    @Override
    public List<HomeCourseVO> selectCourseList(String key) {
        return courseBaseDao.selectCourseList(key);
    }
}
