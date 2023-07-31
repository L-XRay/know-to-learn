package com.cqupt.knowtolearn.model.po.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ray
 * @date 2023/7/31 10:04
 * @description
 */
@Data
@TableName("station_message")
public class StationMessage {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String title;

    private String content;

    private LocalDateTime createTime;

    private Integer status;

}
