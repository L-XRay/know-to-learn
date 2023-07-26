package com.cqupt.knowtolearn.service.captcha;

/**
 * @author Ray
 * @date 2023/7/25 09:38
 * @description
 */
public interface ICaptchaGenerator {

    /**
     * 验证码生成
     * @return 验证码
     */
    String generate(int length);

}
