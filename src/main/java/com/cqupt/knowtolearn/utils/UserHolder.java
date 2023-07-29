package com.cqupt.knowtolearn.utils;

import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.model.po.user.User;

/**
 * @author Ray
 * @date 2023/7/21 10:12
 * @description
 */
public class UserHolder {
    private static final ThreadLocal<Integer> userThreadLocal = new ThreadLocal<>();

    public static void saveUser(Integer user){
        userThreadLocal.set(user);
    }

    public static Integer getUser(){
        return userThreadLocal.get();
    }

    public static void removeUser(){
        userThreadLocal.remove();
    }

}