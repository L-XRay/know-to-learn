package com.cqupt.knowtolearn.service.comment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.knowtolearn.model.dto.req.CommentReq;
import com.cqupt.knowtolearn.model.po.comment.Comment;
import com.cqupt.knowtolearn.model.po.course.CourseBase;

/**
 * @author Ray
 * @date 2023/8/1 17:41
 * @description
 */
public interface ICommentService extends IService<Comment> {

    void addComment(Integer userId, CommentReq req);
}
