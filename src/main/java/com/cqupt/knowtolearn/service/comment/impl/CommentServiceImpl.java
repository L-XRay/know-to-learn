package com.cqupt.knowtolearn.service.comment.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.dao.comment.ICommentDao;
import com.cqupt.knowtolearn.dao.course.ICourseBaseDao;
import com.cqupt.knowtolearn.model.dto.req.CommentReq;
import com.cqupt.knowtolearn.model.po.comment.Comment;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.service.comment.ICommentService;
import com.cqupt.knowtolearn.service.course.ICourseBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author Ray
 * @date 2023/8/1 17:42
 * @description
 */
@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<ICommentDao, Comment> implements ICommentService {

    @Resource
    private ICommentDao commentDao;

    @Override
    public void addComment(Integer userId,CommentReq req) {
        Comment comment = new Comment();
        comment.setCourseId(req.getCourseId());
        comment.setParentId(req.getParentId());
        comment.setContent(req.getContent());
        comment.setUserId(userId);
        comment.setCreateTime(LocalDateTime.now());
        int insert = commentDao.insert(comment);
        if (insert!=1) {
            throw new RuntimeException("评论失败");
        }
    }
}
