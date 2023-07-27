package com.cqupt.knowtolearn.interceptor;

import com.cqupt.knowtolearn.model.dto.res.LoginRes;
import com.cqupt.knowtolearn.utils.JwtUtil;
import com.cqupt.knowtolearn.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Ray
 * @date 2023/7/21 11:35
 * @description
 */
public class RefreshTokenInterceptor implements HandlerInterceptor {


    private StringRedisTemplate redisTemplate;

    private JwtUtil jwtUtil;

    public RefreshTokenInterceptor(StringRedisTemplate redisTemplate,JwtUtil jwtUtil) {
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getHeader("Authorization")==null) {
            return true;
        }
        String token = request.getHeader("Authorization").substring(7);
        boolean isExpired = jwtUtil.isExpired(token);
        if (isExpired) {
            return true;
        }
//        LoginRes res = new LoginRes();
//        res.setUsername((String) map.get("username"));
//        res.setNickname((String) map.get("nickname"));
//        res.setEmail((String) map.get("email"));
        // 刷新有效期
        String currentToken = jwtUtil.refreshToken(token);
        response.setHeader("Authorization","Bearer " + currentToken);
//        res.setToken(currentToken);
        // 保存用户
//        UserHolder.saveUser(res);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 避免 ThreadLocal key 弱引用 造成内存泄漏
//        UserHolder.removeUser();
    }
}
