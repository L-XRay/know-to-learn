package com.cqupt.knowtolearn.model.dto.req;

/**
 * @author Ray
 * @date 2023/7/27 16:09
 * @description
 */
public class VerifyReq {

    String code;

    String key;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
