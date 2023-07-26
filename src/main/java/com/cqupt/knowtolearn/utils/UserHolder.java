package com.cqupt.knowtolearn.utils;

import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.model.po.user.User;

/**
 * @author Ray
 * @date 2023/7/21 10:12
 * @description
 */
public class UserHolder {
    private static final ThreadLocal<LoginRes> userThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> userAgentThreadLocal = new ThreadLocal<>();

    public static void saveUser(LoginRes user){
        userThreadLocal.set(user);
    }

    public static LoginRes getUser(){
        return userThreadLocal.get();
    }

    public static void removeUser(){
        userThreadLocal.remove();
    }

    public static void saveUserAgent(String userAgent){
        userAgentThreadLocal.set(userAgent);
    }

    public static String getUserAgent(){
        return userAgentThreadLocal.get();
    }

    public static void removeUserAgent(){
        userAgentThreadLocal.remove();
    }
}