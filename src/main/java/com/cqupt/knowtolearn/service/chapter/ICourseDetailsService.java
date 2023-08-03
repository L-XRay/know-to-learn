package com.cqupt.knowtolearn.service.chapter;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.model.dto.CourseDetailDTO;
import com.cqupt.knowtolearn.model.dto.req.ChapterReq;
import com.cqupt.knowtolearn.model.dto.req.MediaReq;
import com.cqupt.knowtolearn.model.po.chapter.CourseDetails;
import com.cqupt.knowtolearn.model.vo.CourseDetailVO;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author Ray
 * @date 2023/8/1 10:15
 * @description
 */
public interface ICourseDetailsService extends IService<CourseDetails> {

    Integer addCourseChapter(ChapterReq req);

    List<Map<String,Object>> getChapter(Integer courseId);

    Map<String,Object> addChapterMedia(MediaReq req);

    List<Map<String,Object>> getMedia(Integer chapterId);

    void deleteChapter(Integer chapterId);

    void updateChapter(Integer chapterId, String chapterName);

    boolean alterStatus(Integer mediaId, Enum<Constants.MediaState> beforeState, Enum<Constants.MediaState> afterState);

    Map<String,Object> getCourseDetail(Integer userId, Integer courseId);

    List<CourseDetailVO> getPendingMediaList();
}
