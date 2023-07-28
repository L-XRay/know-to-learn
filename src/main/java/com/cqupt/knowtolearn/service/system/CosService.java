package com.cqupt.knowtolearn.service.system;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.knowtolearn.config.CosConfig;
import com.cqupt.knowtolearn.dao.user.IUserDao;
import com.cqupt.knowtolearn.exception.KnowException;
import com.cqupt.knowtolearn.model.dto.req.CosReq;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.utils.DateUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author Ray
 * @date 2023/7/28 10:32
 * @description
 */
@Service
public class CosService {

    @Resource
    private CosConfig cosConfig;

    @Resource
    private IUserDao userDao;

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



    // 创建 COSClient 实例，这个实例用来后续调用请求
    public COSClient createCosClient(String secretId, String secretKey, String token)  {
        COSCredentials cred = new BasicSessionCredentials(secretId, secretKey, token);
        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(new Region("ap-chengdu"));
        clientConfig.setHttpProtocol(HttpProtocol.http);
        // 设置 socket 读取超时，默认 30s
        clientConfig.setSocketTimeout(30*1000);
        // 设置建立连接超时，默认 30s
        clientConfig.setConnectionTimeout(30*1000);
        // 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    public URL getSignature(HttpMethodName httpMethodName, CosReq cosReq) {
        Map<String, Object> credential = getCredential();
        String secretId = credential.get("secretId").toString();
        String secretKey = credential.get("secretKey").toString();
        String token = credential.get("token").toString();
        COSClient cosClient = createCosClient(secretId, secretKey, token);

        String bucketName = cosConfig.getBucket();

        // 对象键(Key)是对象在存储桶中的唯一标识。详情请参见 [对象键](https://cloud.tencent.com/document/product/436/13324)
        String key = cosReq.getFileName();

        if ("PUT".equals(cosReq.getType())) {
            key = getPath(cosReq.getSuffix());
        }

        if ("user".equals(cosReq.getRegion())) {
            User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, cosReq.getId()));
            if ("PUT".equals(cosReq.getType())) {
                user.setAvatar(key);
            }
            if ("DELETE".equals(cosReq.getType())){
                user.setAvatar(null);
            }
            userDao.updateById(user);
        }

        if ("course".equals(cosReq.getRegion())) {
            // TODO 课程
        }

        // 设置签名过期时间(可选), 若未进行设置则默认使用 ClientConfig 中的签名过期时间(1小时)
        // 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

//        // 填写本次请求的参数，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的参数
//        Map<String, String> params = new HashMap<>();
//        params.put("param1", "value1");
//
//
//        // 填写本次请求的头部，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的头部
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("header1", "value1");


        // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE

        return cosClient.generatePresignedUrl(bucketName, key, expirationDate, httpMethodName);
    }


    private String getPath(String suffix) {
        return System.currentTimeMillis() + UUID.randomUUID(false).toString() + "." + suffix;
    }

}
