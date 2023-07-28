package com.cqupt.knowtolearn.model.dto.req;

/**
 * @author Ray
 * @date 2023/7/28 16:08
 * @description
 */
public class CosReq {

    private String type;

    private Integer id;  // 操作对应数据在数据库的主键

    private String region; // 操作资源类型 user: 头像； course: 媒体资源

    private String fileName; // 文件名,包含后缀,查看对应资源时会返回

    private String suffix;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
