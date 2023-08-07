package com.cqupt.knowtolearn.controller;

import cn.hutool.core.lang.UUID;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.model.dto.req.CaptchaReq;
import com.cqupt.knowtolearn.model.dto.req.VerifyReq;
import com.cqupt.knowtolearn.model.dto.res.CaptchaRes;
import com.cqupt.knowtolearn.service.captcha.CaptchaStrategy;
import com.cqupt.knowtolearn.service.captcha.ICaptchaService;
import com.cqupt.knowtolearn.service.captcha.ICaptchaStore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Ray
 * @date 2023/7/26 17:56
 * @description
 */
@RestController
@CrossOrigin("")
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    private CaptchaStrategy captchaStrategy;

    @Resource
    private ICaptchaStore redisCaptchaStore;


    @PostMapping(value = "/generate")
    public Result generateCaptcha(HttpServletRequest request, @RequestBody(required = false) CaptchaReq captchaReq){
        String type = captchaReq.getType();
        String email = captchaReq.getEmail();
        // 判断邮箱是否已经发送过
        CaptchaRes isExpired = captchaStrategy.isExpired(type, email);
        if (isExpired!=null) {
            return Result.build(400,"fail","您之前的验证码还未使用,请仔细查收邮箱",isExpired);
        }
        ICaptchaService captchaService = captchaStrategy.getCaptchaService(type);
        CaptchaRes res = captchaService.generate(captchaReq);
        return Result.success("发送验证码成功",res);
    }

    @PostMapping(value = "/verify")
    public Result verify(HttpServletRequest request, @RequestBody VerifyReq req){
        boolean verify = captchaStrategy.getCaptchaService("pic").verify(req.getKey(), req.getCode());
        if (verify) {
            String s = UUID.randomUUID().toString();
            redisCaptchaStore.set("verify:" + s,"",1);
            return Result.success("校验成功",s);
        }
        return Result.fail("校验失败");
    }

}
