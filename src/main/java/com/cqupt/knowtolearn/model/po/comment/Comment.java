package com.cqupt.knowtolearn.model.po.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ray
 * @date 2023/8/1 17:37
 * @description
 */
@Data
@TableName("course_comment")
public class Comment {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer courseId;

    private Integer parentId;

    private String content;

    private LocalDateTime createTime;

}
