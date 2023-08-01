package com.cqupt.knowtolearn.service.chapter.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.dao.chapter.ICourseDetailsDao;
import com.cqupt.knowtolearn.model.dto.AlterCourseStateDTO;
import com.cqupt.knowtolearn.model.dto.AlterMediaStateDTO;
import com.cqupt.knowtolearn.model.dto.req.ChapterReq;
import com.cqupt.knowtolearn.model.dto.req.MediaReq;
import com.cqupt.knowtolearn.model.dto.res.CosRes;
import com.cqupt.knowtolearn.model.po.chapter.CourseDetails;
import com.cqupt.knowtolearn.service.chapter.ICourseDetailsService;
import com.cqupt.knowtolearn.service.system.impl.CosService;
import com.qcloud.cos.http.HttpMethodName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ray
 * @date 2023/8/1 10:16
 * @description
 */
@Slf4j
@Service
public class CourseDetailsServiceImpl extends ServiceImpl<ICourseDetailsDao, CourseDetails> implements ICourseDetailsService {

    @Resource
    private ICourseDetailsDao courseDetailsDao;

    @Resource
    private CosService cosService;

    @Override
    public void addCourseChapter(ChapterReq req) {
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setCourseId(req.getCourseId());
        courseDetails.setChapterName(req.getChapterName());
        courseDetails.setMedia(null);
        courseDetails.setCreateDate(LocalDateTime.now());
        courseDetails.setStatus(5);
        courseDetails.setOrderBy(req.getOrderBy());
        courseDetails.setParentId(0);
        int insert = courseDetailsDao.insert(courseDetails);
        if(insert!=1){
            throw new RuntimeException("章节创建失败");
        }
    }

    @Override
    public List<Map<String,Object>> getChapter(Integer courseId) {
        List<Map<String,Object>> list = new ArrayList<>();
        List<CourseDetails> courseDetails = courseDetailsDao.selectList(new LambdaQueryWrapper<CourseDetails>()
                .eq(CourseDetails::getCourseId, courseId)
                .eq(CourseDetails::getParentId,0));
        for (CourseDetails c : courseDetails) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",c.getId());
            map.put("name",c.getChapterName());
            map.put("orderBy",c.getOrderBy());
            list.add(map);
        }
        return list;
    }

    @Override
    public URL addChapterMedia(MediaReq req) {
        CosRes signature = cosService.getOrgMaterialSignature(HttpMethodName.PUT, req.getSuffix());
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setParentId(req.getChapterId());
        courseDetails.setCourseId(req.getCourseId());
        courseDetails.setMedia(signature.getResourceURL());
        courseDetails.setChapterName(req.getName());
        courseDetails.setStatus(1);
        courseDetails.setCreateDate(LocalDateTime.now());
        courseDetails.setOrderBy(req.getOrderBy());
        int insert = courseDetailsDao.insert(courseDetails);
        if(insert!=1){
            throw new RuntimeException("章节视频创建失败");
        }
        return signature.getRequestURL();
    }

    @Override
    public List<Map<String, Object>> getMedia(Integer chapterId) {
        List<Map<String,Object>> list = new ArrayList<>();
        List<CourseDetails> courseDetails = courseDetailsDao.selectList(new LambdaQueryWrapper<CourseDetails>()
                .eq(CourseDetails::getParentId, chapterId));
        for (CourseDetails c : courseDetails) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",c.getId());
            map.put("name",c.getChapterName());
            map.put("url",c.getMedia());
            map.put("status",c.getStatus());
            map.put("orderBy",c.getOrderBy());
            list.add(map);
        }
        return list;
    }

    @Override
    public boolean alterStatus(Integer mediaId, Enum<Constants.MediaState> beforeState, Enum<Constants.MediaState> afterState) {
        AlterMediaStateDTO req = new AlterMediaStateDTO(mediaId,((Constants.MediaState)beforeState).getCode(),((Constants.MediaState)afterState).getCode());
        int count = courseDetailsDao.alterState(req);
        return 1 == count;
    }
}
