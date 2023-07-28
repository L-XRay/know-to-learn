package com.cqupt.knowtolearn.controller;

import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.config.CosConfig;
import com.cqupt.knowtolearn.service.system.CosService;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/getCredential")
    public Result getCredential(HttpServletRequest request) {
        Map<String, Object> data = cosService.getCredential();
        return Result.success("获取cos临时凭证成功",data);
    }
}
