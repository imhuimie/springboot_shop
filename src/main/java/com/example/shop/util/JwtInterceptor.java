package com.example.shop.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper mapper; // JSON序列化器

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String token = requestTokenHeader.substring(7);
            if (!jwtUtil.isExpired(token)) {
                // 管理端页面权限校验
                String uri = request.getRequestURI();
                if (uri.startsWith("/a")) {
                    if (jwtUtil.validateToken(token, Role.Admin)) {
                        return true;
                    }
                    intercept(response, R.of(400, "权限不足", null));
                    return false;
                }
                // 普通用户页面直接放行
                return true;
            }
        }
        intercept(response, R.of(400, "Token无效", null));
        return false;
    }

    // 响应返回
    private void intercept(HttpServletResponse resp, Object obj) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setHeader("Content-Type", "application/json;charset=UTF-8");
        String rs = mapper.writeValueAsString(obj);
        PrintWriter out = resp.getWriter();
        out.write(rs);
        out.flush();
    }
}
