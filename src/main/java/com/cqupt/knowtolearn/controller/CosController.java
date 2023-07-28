package com.cqupt.knowtolearn.controller;

import cn.hutool.core.lang.UUID;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.config.CosConfig;
import com.cqupt.knowtolearn.model.dto.req.CosReq;
import com.cqupt.knowtolearn.service.system.CosService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.http.HttpMethodName;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Ray
 * @date 2023/7/28 10:22
 * @description
 */
@RestController
@RequestMapping("/cos")
public class CosController {

    @Resource
    private CosService cosService;

    @PostMapping("/getSignature")
    public Result getSignatureUp(HttpServletRequest request,@RequestBody CosReq cosReq) {
        URL signature = null;
        if ("PUT".equals(cosReq.getType())) {
            signature = cosService.getSignature(HttpMethodName.PUT,cosReq);
        }
        if ("GET".equals(cosReq.getType())) {
            signature = cosService.getSignature(HttpMethodName.GET,cosReq);
        }
        if ("DELETE".equals(cosReq.getType())) {
            signature = cosService.getSignature(HttpMethodName.DELETE,cosReq);
        }
        return Result.success("获取cos签名URL成功",signature);
    }

}
