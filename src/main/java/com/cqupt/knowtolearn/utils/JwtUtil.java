package com.cqupt.knowtolearn.utils;

import com.cqupt.knowtolearn.exception.KnowException;
import io.fusionauth.jwt.*;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.rsa.RSASigner;
import io.fusionauth.jwt.rsa.RSAVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author Ray
 * @date 2023/7/26 17:09
 * @description
 */
@Component
public class JwtUtil {

    @Value("${jwt.rsa-public-key-path}")
    private String rsaPublicKeyPath;

    @Value("${jwt.rsa-private-key-path}")
    private String rsaPrivateKeyPath;

    @Value("${jwt.ttl}")
    private int ttl;

    @Autowired
    private ResourceLoader resourceLoader;
    private final FileUtil fileUtil = new FileUtil();

    private String getRsaPrivateKey() {
        try {
            Resource resource = resourceLoader.getResource("file:" + rsaPrivateKeyPath);
            InputStream inputStream = resource.getInputStream();
            return fileUtil.readFileToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getRsaPublicKey() {
        try {
            Resource resource = resourceLoader.getResource("file:" + rsaPublicKeyPath);
            InputStream inputStream = resource.getInputStream();
            return fileUtil.readFileToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encodeToken(Map<String, Object> payload) {
        String privateKey = getRsaPrivateKey();
        if (privateKey == null) {
            throw new KnowException("私钥不可用");
        }
        Signer signer = RSASigner.newSHA256Signer(getRsaPrivateKey()); // 用私钥签名

        JWT jwt = new JWT()
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))  // 设置签发时间
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(ttl)); // 设置超时时间

        for(Map.Entry<String, Object> entry : payload.entrySet()){
            jwt.addClaim(entry.getKey(), entry.getValue());
        }

        return JWT.getEncoder().encode(jwt, signer);
    }

    public Map<String, Object> decodeToken(String token) throws JWTExpiredException {
        Verifier verifier = RSAVerifier.newVerifier(getRsaPublicKey());  // 用公钥解密

        JWT jwt = null;
        try {
            jwt = JWT.getDecoder().decode(token, verifier);
            if(jwt.isExpired()) {
                return null;
            }
        } catch (JWTExpiredException | InvalidJWTException e) {
            throw e;
        }

        return jwt.getAllClaims();
    }

    public boolean isExpired(String token) {
        Verifier verifier = RSAVerifier.newVerifier(getRsaPublicKey());  // 用公钥解密
        JWT jwt = null;
        try {
            jwt = JWT.getDecoder().decode(token, verifier);
        } catch (JWTExpiredException | InvalidJWTException e) {
            throw e;
        }
        return jwt.isExpired();
    }

    public String refreshToken(String token) {
        // 首先验证原始令牌
        Verifier verifier = RSAVerifier.newVerifier(getRsaPublicKey());
        JWT jwt = null;
        try {
            jwt = JWT.getDecoder().decode(token, verifier);
            // 如果原始令牌已过期，则拒绝续期
            if (jwt.isExpired()) {
                throw new JWTExpiredException();
            }
        } catch (JWTExpiredException e) {
            throw e;
        }

        // 更新令牌的过期时间
        jwt.setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(ttl));
        // 重新签名令牌
        Signer signer = RSASigner.newSHA256Signer(getRsaPrivateKey());
        return JWT.getEncoder().encode(jwt, signer);
    }

}
