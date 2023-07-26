package com.cqupt.knowtolearn.service.captcha.impl;

import cn.hutool.core.lang.UUID;
import com.cqupt.knowtolearn.model.dto.req.CaptchaReq;
import com.cqupt.knowtolearn.model.dto.res.CaptchaRes;
import com.cqupt.knowtolearn.service.captcha.AbstractCaptchaService;
import com.cqupt.knowtolearn.service.captcha.ICaptchaService;
import com.cqupt.knowtolearn.service.captcha.ICaptchaStore;
import com.cqupt.knowtolearn.utils.EncryptUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Ray
 * @date 2023/7/25 15:45
 * @description
 */
@Service("picCaptchaService")
public class PicCaptchaServiceImpl extends AbstractCaptchaService implements ICaptchaService {

    @Resource
    private DefaultKaptcha captcha;

    @Resource
    private ICaptchaStore redisCaptchaStore;

    @Override
    public CaptchaRes generate(CaptchaReq captchaReq, String verify) {
        GenerateResult generate = generate(captchaReq, 4, "captcha:", 3);
        String key = generate.getKey();
        String code = generate.getCode();
        String pic = createPic(code);
        CaptchaRes res = new CaptchaRes();
        String s = UUID.randomUUID().toString();
        redisCaptchaStore.set("verify:" + s,"",1);
        res.setAliasing(pic);
        res.setKey(key);
        res.setTtl(redisCaptchaStore.ttl(key));
        res.setPayLoad(s);
        return res;
    }

    private String createPic(String code) {
        // 生成图片验证码
        ByteArrayOutputStream outputStream = null;
        BufferedImage image = captcha.createImage(code);

        outputStream = new ByteArrayOutputStream();
        String imgBase64Encoder = null;
        try {
            // 对字节数组Base64编码
            BASE64Encoder base64Encoder = new BASE64Encoder();
            ImageIO.write(image, "png", outputStream);
            imgBase64Encoder = "data:image/png;base64," + EncryptUtil.encodeBase64(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgBase64Encoder;
    }
}
