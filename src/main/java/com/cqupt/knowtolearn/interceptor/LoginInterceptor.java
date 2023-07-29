package com.cqupt.knowtolearn.interceptor;

import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.utils.JwtUtil;
import com.cqupt.knowtolearn.utils.UserHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Ray
 * @date 2023/7/21 00:34
 * @description
 */
public class LoginInterceptor implements HandlerInterceptor {


    private JwtUtil jwtUtil;

    public LoginInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getHeader("Authorization")==null) {
            return false;
        }
        if (UserHolder.getUser()==null) {
            return false;
        }
        String token = request.getHeader("Authorization").substring(7);
//        System.out.println(token);
        boolean isExpired = jwtUtil.isExpired(token);
        if (isExpired) {
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 避免 ThreadLocal key 弱引用 造成内存泄漏
        UserHolder.removeUser();
    }
}
