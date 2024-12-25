package com.example.shop.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    // 不做校验的URI
    private final String[] excludePath = {
            "/auth/login",      // 用户登录
            "/a/auth/login",    // 管理员登录
            "/user/add",        // 用户注册
            "/file/**.**"       // 上传的图片文件
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns(excludePath) // 排除某些路径
                .order(1); // 指定拦截器的顺序
    }
}
