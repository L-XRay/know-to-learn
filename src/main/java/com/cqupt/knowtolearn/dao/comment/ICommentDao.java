package com.cqupt.knowtolearn.dao.comment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.knowtolearn.model.po.chapter.CourseDetails;
import com.cqupt.knowtolearn.model.po.comment.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Ray
 * @date 2023/8/1 17:39
 * @description
 */
@Mapper
public interface ICommentDao extends BaseMapper<Comment> {
}
