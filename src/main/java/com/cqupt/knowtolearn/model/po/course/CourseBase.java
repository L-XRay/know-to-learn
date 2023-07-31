package com.cqupt.knowtolearn.model.po.course;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Ray
 * @date 2023/7/28 22:37
 * @description
 */
@Data
@TableName("course_base")
public class CourseBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 机构ID
     */
    private Integer orgId;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 授课教师
     */
    private String teachers;

    /**
     * 课程标签
     */
    private String tags;

    /**
     * 分类
     */
    private String category;

    /**
     * 课程介绍
     */
    private String description;

    /**
     * 课程封面
     */
    private String pic;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changeDate;

    /**
     * 发布时间
     */
    private LocalDateTime publishDate;

    /**
     * 课程状态
     */
    private Integer status;

}
