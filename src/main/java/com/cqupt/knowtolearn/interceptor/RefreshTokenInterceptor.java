package com.cqupt.knowtolearn.interceptor;

import com.cqupt.knowtolearn.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author Ray
 * @date 2023/7/21 11:35
 * @description
 */
public class RefreshTokenInterceptor implements HandlerInterceptor {


    private StringRedisTemplate redisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = (String) request.getSession().getAttribute("authorization");
//        if (token==null) {
//            return true;
//        }
//        String username = (String) redisTemplate.opsForHash().get("token:" + token, "username");
//        if (username==null) {
//            return true;
//        }
//        // 保存用户名
//        UserHolder.saveUser(username);
        // 刷新有效期
//        redisTemplate.expire("login:token"+token,30L, TimeUnit.MINUTES);
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
