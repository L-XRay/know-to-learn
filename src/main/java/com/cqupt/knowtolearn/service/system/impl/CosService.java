package com.cqupt.knowtolearn.service.system.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.knowtolearn.config.CosConfig;
import com.cqupt.knowtolearn.dao.chapter.ICourseDetailsDao;
import com.cqupt.knowtolearn.dao.course.ICourseBaseDao;
import com.cqupt.knowtolearn.dao.user.IUserDao;
import com.cqupt.knowtolearn.exception.KnowException;
import com.cqupt.knowtolearn.model.dto.req.CosReq;
import com.cqupt.knowtolearn.model.dto.res.CosRes;
import com.cqupt.knowtolearn.model.po.chapter.CourseDetails;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.service.course.ICourseBaseService;
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

    @Resource
    private ICourseDetailsDao courseDetailsDao;

    @Resource
    private ICourseBaseDao courseBaseDao;

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

    public CosRes getAvatarSignature(HttpMethodName httpMethodName, Integer userId, String suffix) {
        Map<String, Object> credential = getCredential();
        String secretId = credential.get("secretId").toString();
        String secretKey = credential.get("secretKey").toString();
        String token = credential.get("token").toString();
        COSClient cosClient = createCosClient(secretId, secretKey, token);

        String bucketName = cosConfig.getBucket();

//        // 对象键(Key)是对象在存储桶中的唯一标识。详情请参见 [对象键](https://cloud.tencent.com/document/product/436/13324)
//        String key = cosReq.getFileName();

//        if ("PUT".equals(cosReq.getType())) {
          String key = getPath(suffix);
//        }

//        if ("user".equals(cosReq.getRegion())) {
            User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, userId));
            if (HttpMethodName.PUT == httpMethodName) {
                user.setAvatar(cosConfig.getVisitUrl() + key);
            }
            if (HttpMethodName.DELETE == httpMethodName){
                user.setAvatar(null);
            }
            userDao.updateById(user);
//        }



        // 设置签名过期时间(可选), 若未进行设置则默认使用 ClientConfig 中的签名过期时间(1小时)
        // 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

        // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        URL url = cosClient.generatePresignedUrl(bucketName, key, expirationDate, httpMethodName);
        CosRes cosRes = new CosRes();
        cosRes.setRequestURL(url);
        cosRes.setResourceURL(cosConfig.getVisitUrl() + key);
        return cosRes;
    }


    private String getPath(String suffix) {
        return System.currentTimeMillis() + UUID.randomUUID(false).toString() + "." + suffix;
    }

    public CosRes getOrgMaterialSignature(HttpMethodName httpMethodName, String suffix) {
        Map<String, Object> credential = getCredential();
        String secretId = credential.get("secretId").toString();
        String secretKey = credential.get("secretKey").toString();
        String token = credential.get("token").toString();
        COSClient cosClient = createCosClient(secretId, secretKey, token);
        String bucketName = cosConfig.getBucket();
        String key = getPath(suffix);
        Date expirationDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
        // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        URL url = cosClient.generatePresignedUrl(bucketName, key, expirationDate, httpMethodName);
        CosRes cosRes = new CosRes();
        cosRes.setRequestURL(url);
        cosRes.setResourceURL(cosConfig.getVisitUrl() + key);
        return cosRes;
    }

    public CosRes getCourseMediaSignature(HttpMethodName httpMethodName, Integer chapterId, String chapterName, String suffix) {
        Map<String, Object> credential = getCredential();
        String secretId = credential.get("secretId").toString();
        String secretKey = credential.get("secretKey").toString();
        String token = credential.get("token").toString();
        COSClient cosClient = createCosClient(secretId, secretKey, token);

        String bucketName = cosConfig.getBucket();

//        // 对象键(Key)是对象在存储桶中的唯一标识。详情请参见 [对象键](https://cloud.tencent.com/document/product/436/13324)
//        String key = cosReq.getFileName();

//        if ("PUT".equals(cosReq.getType())) {
        String key = getPath(suffix);
//        }

//        if ("user".equals(cosReq.getRegion())) {
        CourseDetails courseDetails = courseDetailsDao.selectById(chapterId);
        if (HttpMethodName.PUT == httpMethodName) {
            courseDetails.setStatus(1);
            courseDetails.setMedia(cosConfig.getVisitUrl() + key);
            if (chapterName!=null) {
                courseDetails.setChapterName(chapterName);
            }
        }
        if (HttpMethodName.DELETE == httpMethodName){
            courseDetails.setMedia(null);
        }
        courseDetailsDao.updateById(courseDetails);
//        }



        // 设置签名过期时间(可选), 若未进行设置则默认使用 ClientConfig 中的签名过期时间(1小时)
        // 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

        // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        URL url = cosClient.generatePresignedUrl(bucketName, key, expirationDate, httpMethodName);
        CosRes cosRes = new CosRes();
        cosRes.setRequestURL(url);
        cosRes.setResourceURL(cosConfig.getVisitUrl() + key);
        return cosRes;
    }

}
