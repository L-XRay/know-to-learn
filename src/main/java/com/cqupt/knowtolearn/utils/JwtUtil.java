package com.cqupt.knowtolearn.utils;

import io.fusionauth.jwt.JWTExpiredException;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
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
            // Handle the case when the private key is not available
            throw new IllegalStateException("Private key is not available");
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

        JWT jwt = JWT.getDecoder().decode(token, verifier);

        if(jwt.isExpired()) return null;

        return jwt.getAllClaims();
    }

}
