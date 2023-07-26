package com.cqupt.knowtolearn.service.captcha;

import com.cqupt.knowtolearn.model.dto.req.CaptchaReq;
import com.cqupt.knowtolearn.model.dto.res.CaptchaRes;

/**
 * @author Ray
 * @date 2023/7/25 09:27
 * @description
 */
public interface ICaptchaService {

    /**
     * 生成验证码
     * @param captchaReq 请求参数
     * @return res
     */
    CaptchaRes generate(CaptchaReq captchaReq, String verify);

    /**
     * 校验验证码
     * @param key   key
     * @param code  code
     * @return 校验结果
     */
    boolean verify(String key, String code);

}
