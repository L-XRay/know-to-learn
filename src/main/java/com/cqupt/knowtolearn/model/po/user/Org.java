package com.cqupt.knowtolearn.model.po.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Ray
 * @date 2023-07-26
 * @description 
 */
@Data
@TableName("org")
public class Org implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    /**
     * 联系人
     */
    private int userId;

    private String name;

    /**
     * 简介
     */
    private String intro;

    /**
     * 营业执照
     */
    private String materials;

    private LocalDateTime createTime;

    /**
     * 企业状态
     */
    private Integer status;


}
