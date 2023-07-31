package com.cqupt.knowtolearn.model.vo;

/**
 * @author Ray
 * @date 2023/7/31 11:27
 * @description
 */
public class StationMessageVO {

    private String title;

    private String content;

    private Long createTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
