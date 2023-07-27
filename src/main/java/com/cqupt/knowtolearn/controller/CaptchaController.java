package com.cqupt.knowtolearn.controller;

import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.model.dto.req.CaptchaReq;
import com.cqupt.knowtolearn.model.dto.res.CaptchaRes;
import com.cqupt.knowtolearn.service.captcha.CaptchaStrategy;
import com.cqupt.knowtolearn.service.captcha.ICaptchaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Ray
 * @date 2023/7/26 17:56
 * @description
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    private CaptchaStrategy captchaStrategy;

    @PostMapping(value = "/generate")
    public Result generateCaptcha(HttpServletRequest request,CaptchaReq captchaReq, String verify){
        String type = captchaReq.getType();
        ICaptchaService captchaService = captchaStrategy.getCaptchaService(type);
        CaptchaRes res = captchaService.generate(captchaReq, verify);
        return Result.success("发送验证码成功",res);
    }

    @PostMapping(value = "/verify")
    public Result verify(String key, String code){
        boolean verify = captchaStrategy.getCaptchaService("pic").verify(key, code);
        if (verify) {
            return Result.success("校验成功",true);
        }
        return Result.success("校验成功",false);
    }

}
