package com.cqupt.knowtolearn.model.dto.req;

/**
 * @author Ray
 * @date 2023/7/30 15:26
 * @description
 */
public class OrgReq {

    private String orgName;

    private String introduction;

    private String materials;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }
}
