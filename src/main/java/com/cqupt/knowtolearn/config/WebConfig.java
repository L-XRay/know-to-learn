package com.cqupt.knowtolearn.config;

import com.cqupt.knowtolearn.interceptor.AdminInterceptor;
import com.cqupt.knowtolearn.interceptor.LoginInterceptor;
import com.cqupt.knowtolearn.interceptor.OrgInterceptor;
import com.cqupt.knowtolearn.interceptor.RefreshTokenInterceptor;
import com.cqupt.knowtolearn.service.captcha.ICaptchaStore;
import com.cqupt.knowtolearn.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * @author Ray
 * @date 2023/7/21 08:41
 * @description
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private JwtUtil jwtUtil;

    public static final String URL="/chief/**";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(URL).addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(jwtUtil))
                .excludePathPatterns(
                        "/user/login",
                        "/user/refresh/token",
                        "/captcha/**",
                        "/chief/**",
                        "/course/recommendation",
                        "/search").order(1);
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate,jwtUtil))
                .addPathPatterns("/**").order(0);

        registry.addInterceptor(new AdminInterceptor(jwtUtil)).addPathPatterns("/admin/**").order(2);
        registry.addInterceptor(new OrgInterceptor(jwtUtil)).addPathPatterns("/org/**").order(2);
    }

    @Bean
    public Filter corsFilter() {
        return new CorsFilter();
    }
}
