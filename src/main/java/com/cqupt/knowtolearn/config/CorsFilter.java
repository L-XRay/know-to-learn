package com.cqupt.knowtolearn.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ray
 * @date 2023/8/7 11:11
 * @description
 */
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 设置允许的跨域来源域，可以使用通配符 * 表示允许所有来源域访问
        response.setHeader("Access-Control-Allow-Origin", "*");

        // 设置允许的HTTP方法，例如："GET, POST, PUT, DELETE, OPTIONS"
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        // 设置允许的自定义请求头，如果前端需要自定义请求头，需要在这里添加
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // 设置是否允许携带认证信息（如Cookie或HTTP认证）
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // 设置预检请求的缓存时间，单位为秒，这里设置为 3600 秒（1小时）
        response.setHeader("Access-Control-Max-Age", "3600");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
