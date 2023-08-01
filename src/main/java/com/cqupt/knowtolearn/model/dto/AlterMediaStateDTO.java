package com.cqupt.knowtolearn.model.dto;

/**
 * @author Ray
 * @date 2023/7/29 15:40
 * @description
 */
public class AlterMediaStateDTO {

    /** 媒资ID */
    private Integer mediaId;

    /** 更新前状态 */
    private Integer beforeState;

    /** 更新后状态 */
    private Integer afterState;

    public AlterMediaStateDTO() {
    }

    public AlterMediaStateDTO(Integer mediaId, Integer beforeState, Integer afterState) {
        this.mediaId = mediaId;
        this.beforeState = beforeState;
        this.afterState = afterState;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getBeforeState() {
        return beforeState;
    }

    public void setBeforeState(Integer beforeState) {
        this.beforeState = beforeState;
    }

    public Integer getAfterState() {
        return afterState;
    }

    public void setAfterState(Integer afterState) {
        this.afterState = afterState;
    }
}
