package com.cqupt.knowtolearn.service.captcha.impl;

import com.cqupt.knowtolearn.exception.KnowException;
import com.cqupt.knowtolearn.model.dto.req.CaptchaReq;
import com.cqupt.knowtolearn.model.dto.res.CaptchaRes;
import com.cqupt.knowtolearn.service.captcha.AbstractCaptchaBase;
import com.cqupt.knowtolearn.service.captcha.ICaptchaService;
import com.cqupt.knowtolearn.service.captcha.ICaptchaStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

/**
 * @author Ray
 * @date 2023/7/25 15:45
 * @description
 */
@Service("emailCaptchaService")
public class EmailCaptchaBaseImpl extends AbstractCaptchaBase implements ICaptchaService {

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Resource
    private ICaptchaStore redisCaptchaStore;

    @Override
    public CaptchaRes generate(CaptchaReq captchaReq) {
        if (redisCaptchaStore.get("verify:" + captchaReq.getVerify()) == null) {
            throw new KnowException("邮件暂时不能发送,请重新获取验证码");
        }
//        String existKey = "email:" + captchaReq.getEmail();
//        if (redisCaptchaStore.hasKey(existKey)) {
//            CaptchaRes res = new CaptchaRes();
//            res.setKey(existKey);
//            res.setTtl(redisCaptchaStore.ttl(existKey));
//            return res;
//        }
        String userEmail = captchaReq.getEmail();
        GenerateResult generate = doGenerate(captchaReq, 4, "email:", 3);
        String key = generate.getKey();
        String code = generate.getCode();
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            context.setVariable("verifyCode", Arrays.asList(code.split("")));
            // 将模块引擎内容解析成html字符串
            String emailContent = templateEngine.process("EmailTemplate", context);
            // 标题
            helper.setSubject("知学随学");
            // 内容
            helper.setText(emailContent,true);
            helper.setFrom(sender);
            helper.setTo(userEmail);
            javaMailSender.send(message);
        } catch (javax.mail.MessagingException e) {
            throw new KnowException("邮件发送失败");
        }
        CaptchaRes res = new CaptchaRes();
        res.setKey(key);
        res.setTtl(redisCaptchaStore.ttl(key));
        redisCaptchaStore.remove("verify:"+ captchaReq.getVerify());
        return res;
    }
}
