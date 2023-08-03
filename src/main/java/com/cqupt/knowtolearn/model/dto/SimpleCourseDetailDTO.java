package com.cqupt.knowtolearn.model.dto;

import java.util.List;

/**
 * @author Ray
 * @date 2023/8/3 16:17
 * @description
 */
public class SimpleCourseDetailDTO {

    private Integer id;

    private Integer pid;

    private String name;

    private String url;

    private List<SimpleCourseDetailDTO> videos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public List<SimpleCourseDetailDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<SimpleCourseDetailDTO> videos) {
        this.videos = videos;
    }
}
