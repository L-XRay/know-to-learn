package com.cqupt.knowtolearn.model.po.chapter;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ray
 * @date 2023/8/1 10:09
 * @description
 */
@Data
@TableName("course_details")
public class CourseDetails {


    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 章节名称
     */
    private String chapterName;

    private Integer courseId;

    private Integer parentId;

    private Integer orderBy;

    private String media;

    private Integer status;

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

}
