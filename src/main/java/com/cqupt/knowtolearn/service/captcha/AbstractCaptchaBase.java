package com.cqupt.knowtolearn.service.captcha;

import com.cqupt.knowtolearn.model.dto.req.CaptchaReq;
import com.cqupt.knowtolearn.model.dto.res.CaptchaRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author Ray
 * @date 2023/7/25 09:30
 * @description
 */
public abstract class AbstractCaptchaBase implements ICaptchaService {

    private final Logger logger = LoggerFactory.getLogger(AbstractCaptchaBase.class);

    @Resource(name = "redisCaptchaStore")
    private ICaptchaStore redisCaptchaStore;

    @Resource(name = "snowFlakeGenerator")
    private IIdGenerator idGenerator;

    @Resource(name = "simpleCaptchaGenerator")
    private ICaptchaGenerator captchaGenerator;

    @Override
    public abstract CaptchaRes generate(CaptchaReq captchaReq, String verify);

    @Override
    public boolean verify(String key, String code) {
        if ((key==null) || (code==null)){
            return false;
        }
        String captcha = redisCaptchaStore.get(key);
        if (captcha == null){
            return false;
        }
        boolean result = captcha.equalsIgnoreCase(code);
        if(result){
            //删除验证码
            redisCaptchaStore.remove(key);
        }
        return result;
    }

    protected static class GenerateResult {
        String key;
        String code;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    /**
     * 生成验证公用方法
     * @param captchaReq 生成验证码参数
     * @param length 验证码长度
     * @param keyPrefix key的前缀
     * @param expire 过期时间
     * @return 生成结果
     */
    public GenerateResult doGenerate(CaptchaReq captchaReq,Integer length,String keyPrefix,Integer expire){
        //生成四位验证码
        String code = captchaGenerator.generate(length);
        String key = null;
        if ("email".equals(captchaReq.getType())) {
            key = keyPrefix + captchaReq.getEmail();
        } else {
            key = idGenerator.nextId(keyPrefix);
        }
        // 存储验证码
        redisCaptchaStore.set(key,code,expire);
        // 返回验证码生成结果
        GenerateResult generateResult = new GenerateResult();
        generateResult.setKey(key);
        generateResult.setCode(code);
        return generateResult;
    }
}
