package com.cqupt.knowtolearn.model.dto;

import java.util.List;

/**
 * @author Ray
 * @date 2023/8/2 11:25
 * @description
 */
public class CourseDetailDTO {

    private Integer id;

    private Integer pid;

    private String videoUrl;

    private String name;

    private Integer orderBy;

    private Integer status;

    private List<CourseDetailDTO> child;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CourseDetailDTO> getChild() {
        return child;
    }

    public void setChild(List<CourseDetailDTO> child) {
        this.child = child;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "CourseDetailDTO{" +
                "id=" + id +
                ", pid=" + pid +
                ", videoUrl='" + videoUrl + '\'' +
                ", name='" + name + '\'' +
                ", orderBy=" + orderBy +
                ", status=" + status +
                ", child=" + child +
                '}';
    }
}
