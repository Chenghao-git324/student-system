package com.example.studentsysteam.config;

import com.example.studentsysteam.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")          // 拦截所有请求
                .excludePathPatterns(
                "/login",         // 登录接口本身
                "/register",      // 注册接口本身
                "/login.html",    // 登录页面
                "/register.html", // 注册页面 ← 新加这行
                "/index.html",
                "/",
                "/error"
        );

    }
}
