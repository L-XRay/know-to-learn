package com.cqupt.knowtolearn.model.dto.res;

/**
 * @author Ray
 * @date 2023/7/25 09:26
 * @description
 */
public class CaptchaRes {

    /**
     * key用于验证
     */
    private String key;

    /**
     * 混淆后的内容
     * 举例：
     * 1.图片验证码为:图片base64编码
     * 2.短信验证码为:null
     * 3.邮件验证码为: null
     * 4.邮件链接点击验证为：null
     * ...
     */
    private String aliasing;

    /**
     * 过期时间
     */
    private Long ttl;

    public String getAliasing() {
        return aliasing;
    }

    public void setAliasing(String aliasing) {
        this.aliasing = aliasing;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }
}
