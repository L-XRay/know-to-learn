package com.cqupt.knowtolearn.aop;

import com.cqupt.knowtolearn.utils.JwtUtil;
import com.cqupt.knowtolearn.utils.UserHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Ray
 * @date 2023/7/26 22:12
 * @description
 */
@Aspect
@Component
public class LoggingAspect {

    @Resource
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @After("execution(* com.cqupt.knowtolearn.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String username;
        HttpServletRequest request = findRequestInArgs(joinPoint.getArgs());
        assert request != null;
        if (request.getHeader("Authorization") == null) {
            username = "未登录用户";
        } else {
            String token = request.getHeader("Authorization").substring(7);
            // 获取当前用户名
            username = (String) jwtUtil.decodeToken(token).get("username");
        }

        // 获取浏览器信息
        String userAgent = request.getHeader("User-Agent");

        // 获取方法名
        String methodName = joinPoint.getSignature().getName();

        // 获取接口信息
        String interfaceInfo = getInterfaceInfo(joinPoint);

        // 获取请求url
        String url = request.getRequestURL().toString();

        // 记录日志
        logger.info("接口访问信息 - 用户名: {}, 浏览器: {}, URL: {}", username, userAgent, url);
    }

    // 辅助方法，获取接口信息
    private String getInterfaceInfo(JoinPoint joinPoint) {
        // 获取类名和方法名
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        // 获取方法参数
        Object[] args = joinPoint.getArgs();

        // 构建接口信息字符串
        StringBuilder interfaceInfo = new StringBuilder();
        interfaceInfo.append(className).append(".").append(methodName).append("(");
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                interfaceInfo.append(", ");
            }
            interfaceInfo.append(args[i]);
        }
        interfaceInfo.append(")");

        return interfaceInfo.toString();
    }

    // 辅助方法，从参数获取请求信息
    private HttpServletRequest findRequestInArgs(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                return (HttpServletRequest) arg;
            }
        }
        return null;
    }
}
