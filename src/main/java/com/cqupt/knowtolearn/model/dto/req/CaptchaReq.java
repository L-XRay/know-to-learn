package com.cqupt.knowtolearn.model.dto.req;

/**
 * @author Ray
 * @date 2023/7/26 18:21
 * @description
 */
public class CaptchaReq {

    /**
     * 验证码类型:pic、sms、email等
     */
    private String type;

    /**
     * 业务携带参数
     */
    private String email;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
