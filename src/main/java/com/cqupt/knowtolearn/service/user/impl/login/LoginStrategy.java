package com.cqupt.knowtolearn.service.user.impl.login;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ray
 * @date 2023/7/27 15:15
 * @description
 */
public class LoginStrategy {

    @Resource(name = "passwordAuthServiceImpl")
    private IAuthService passwordAuthServiceImpl;

    @Resource(name = "emailServiceImpl")
    private IAuthService emailAuthServiceImpl;

    protected static Map<String, IAuthService> authServiceMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        authServiceMap.put("email", emailAuthServiceImpl);
        authServiceMap.put("password",passwordAuthServiceImpl);
    }

}
