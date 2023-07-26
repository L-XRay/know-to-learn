package com.cqupt.knowtolearn.service.captcha;

/**
 * @author Ray
 * @date 2023/7/25 09:34
 * @description
 */
public interface ICaptchaStore {

    void set(String key, String value, Integer expire);

    String get(String key);

    void remove(String key);

    boolean hasKey(String key);

    Long ttl(String key);
}
