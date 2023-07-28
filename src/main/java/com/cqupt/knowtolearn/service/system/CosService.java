package com.cqupt.knowtolearn.service.system;

import com.cqupt.knowtolearn.config.CosConfig;
import com.cqupt.knowtolearn.exception.KnowException;
import com.cqupt.knowtolearn.utils.DateUtil;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Ray
 * @date 2023/7/28 10:32
 * @description
 */
@Service
public class CosService {

    @Resource
    private CosConfig cosConfig;

    public Map<String,Object> getCredential() {
        TreeMap<String, Object> config = cosConfig.getConfig();
        Response response = null;
        try {
            response = CosStsClient.getCredential(config);
        } catch (IOException e) {
            throw new KnowException("Cos获取凭证失败");
        }

        Map<String,Object> map = new HashMap<>();

        Date date = DateUtil.timestampToDate(response.expiredTime);
        String ttl = DateUtil.getDateTime(date);

        map.put("secretId",response.credentials.tmpSecretId);
        map.put("secretKey", response.credentials.tmpSecretKey);
        map.put("token", response.credentials.sessionToken);
        map.put("ttl", ttl);

        return map;
    }
}
