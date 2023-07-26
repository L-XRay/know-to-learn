package com.cqupt.knowtolearn.controller;

import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.model.dto.req.CaptchaReq;
import com.cqupt.knowtolearn.model.dto.res.CaptchaRes;
import com.cqupt.knowtolearn.service.captcha.ICaptchaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Ray
 * @date 2023/7/26 17:56
 * @description
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    private ICaptchaService picCaptchaService;

    @Resource
    private ICaptchaService emailCaptchaService;

    @PostMapping(value = "/generate")
    public Result generateCaptcha(CaptchaReq captchaReq, String verify){
        String type = captchaReq.getType();
        if ("pic".equals(type)) {
            CaptchaRes res = picCaptchaService.generate(captchaReq, null);
            return Result.success("生成图像验证码成功",res);
        }
        CaptchaRes res = emailCaptchaService.generate(captchaReq, verify);
        return Result.success("发送邮箱验证码成功",res);
    }

    @PostMapping(value = "/verify")
    public Result verify(String key, String code){
        boolean verify = picCaptchaService.verify(key, code);
        if (verify) {
            return Result.success("校验成功",true);
        }
        return Result.success("校验成功",false);
    }


}
