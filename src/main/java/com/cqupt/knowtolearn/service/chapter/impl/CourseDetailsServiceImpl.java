package com.cqupt.knowtolearn.service.chapter.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.dao.chapter.ICourseDetailsDao;
import com.cqupt.knowtolearn.dao.course.ICourseBaseDao;
import com.cqupt.knowtolearn.dao.user.IUserDao;
import com.cqupt.knowtolearn.model.dto.AlterCourseStateDTO;
import com.cqupt.knowtolearn.model.dto.AlterMediaStateDTO;
import com.cqupt.knowtolearn.model.dto.CourseDetailDTO;
import com.cqupt.knowtolearn.model.dto.req.ChapterReq;
import com.cqupt.knowtolearn.model.dto.req.MediaReq;
import com.cqupt.knowtolearn.model.dto.res.CosRes;
import com.cqupt.knowtolearn.model.po.chapter.CourseDetails;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.model.vo.CourseDetailVO;
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
import java.util.stream.Collectors;

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
    private IUserDao userDao;

    @Resource
    private ICourseBaseDao courseBaseDao;

    @Resource
    private CosService cosService;

    @Override
    public Integer addCourseChapter(ChapterReq req) {
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
        return courseDetails.getId();
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
    public Map<String,Object> addChapterMedia(MediaReq req) {
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
        Map<String,Object> map = new HashMap<>();
        map.put("url",signature);
        map.put("data",courseDetails);
        return map;
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

    @Override
    public Map<String,Object> getCourseDetail(Integer userId, Integer courseId) {

        User user = userDao.selectById(userId);
        Integer orgId = user.getOrgId();
        boolean isAuthor = courseBaseDao.selectCourseIsOwn(orgId, courseId) == 1;

        List<CourseDetailDTO> list = courseDetailsDao.selectTreeNodes(courseId);
        Map<Integer, CourseDetailDTO> map = list.stream().collect(Collectors.toMap(key -> key.getId(), value -> value));
        List<CourseDetailDTO> data = new ArrayList<>();

        list.forEach(item -> {

            if (item.getPid().equals(0)) {
                data.add(item);
            }
            // 放入父节点的 childrenTreeNodes

            // 找到当前节点的父节点
            // 第一层直接跳过,因为之前已经过滤了传入的根节点,得到的parent为null
            // 二层及以上就可以在map中找到自己的父节点
            CourseDetailDTO parent = map.get(item.getPid());

            if (parent!=null) {
                // 如果第一次遍历到,此时父节点的子节点为null,进行初始化
                if (parent.getChild()==null) {
                    parent.setChild(new ArrayList<>());
                }

                // 放入父节点的 childrenTreeNodes
                parent.getChild().add(item);
            }
        });

        Map<String,Object> res = new HashMap<>();
        res.put("isAuthor",isAuthor);
        res.put("data",data);
        return res;
    }

    @Override
    public void deleteChapter(Integer chapterId) {
        boolean b = this.removeById(chapterId);
        if (!b) {
            throw new RuntimeException("删除章节失败");
        }
    }

    @Override
    public void updateChapter(Integer chapterId, String chapterName) {
        CourseDetails courseDetails = courseDetailsDao.selectById(chapterId);
        courseDetails.setChapterName(chapterName);
        int update = courseDetailsDao.updateById(courseDetails);
        if (update!=1) {
            throw new RuntimeException("修改章节失败");
        }
    }

    @Override
    public List<CourseDetailVO> getPendingMediaList() {
        return courseDetailsDao.selectPendingMediaList();
    }
}
