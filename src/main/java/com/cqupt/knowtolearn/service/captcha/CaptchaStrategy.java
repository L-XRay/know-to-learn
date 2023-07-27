package com.cqupt.knowtolearn.service.captcha;

import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.exception.KnowException;
import com.cqupt.knowtolearn.model.dto.res.CaptchaRes;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Ray
 * @date 2023/7/27 09:29
 * @description
 */
@Component
public class CaptchaStrategy {

    @Resource
    private ICaptchaService picCaptchaService;

    @Resource
    private ICaptchaService emailCaptchaService;

    @Resource
    private ICaptchaStore redisCaptchaStore;

    public ICaptchaService getCaptchaService(String type) {
        if (type==null) {
            return null;
        }
        if ("email".equals(type)) {
            return emailCaptchaService;
        }
        if ("pic".equals(type)) {
            return picCaptchaService;
        }
        throw new KnowException("不存在的验证码类型");
    }

    public CaptchaRes isExpired(String type,String key) {
        String existKey = type + ":" + key;
        if (redisCaptchaStore.hasKey(existKey)) {
            CaptchaRes res = new CaptchaRes();
            res.setKey(existKey);
            res.setTtl(redisCaptchaStore.ttl(existKey));
            return res;
        }
        return null;
    }
}
