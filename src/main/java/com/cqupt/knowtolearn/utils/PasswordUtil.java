package com.cqupt.knowtolearn.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author Ray
 * @date 2023/7/26 17:36
 * @description
 */
@Component
public class PasswordUtil {
    // 生成随机盐
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // 使用 SHA-256 算法对密码进行加密
    public String encryptPassword(String password,String salt) {
        try {
            // 将密码和盐拼接
            String passwordWithSalt = password + salt;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passwordWithSalt.getBytes());
            byte[] bytes = md.digest();

            // 将盐和加密后的密码拼接起来
            StringBuilder sb = new StringBuilder();
            sb.append(salt);
            for (byte b : bytes) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 验证用户输入的密码是否匹配存储的加密密码
    public boolean matches(String inputPassword, String storedEncryptedPassword, String storedSalt) {
        String encryptedInputPassword = encryptPassword(inputPassword, storedSalt);
        return encryptedInputPassword.equals(storedEncryptedPassword);
    }


}