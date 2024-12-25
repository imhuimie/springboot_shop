package com.example.shop.sys.controller;

import com.example.shop.sys.dto.ShopUser;
import com.example.shop.sys.service.AuthService;
import com.example.shop.util.JwtUtil;
import com.example.shop.util.R;
import com.example.shop.util.Role;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public R login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        if (username == null || password == null) {
            return R.of(400, "账号或密码不能为空", null);
        }
        ShopUser user = authService.login(username, password);
        if (user != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("username", user.getUsername());
            String token = jwtUtil.generateToken(map, Role.User);
            return R.ok(token);
        } else {
            return R.of(400, "账号密码不正确", null);
        }
    }

    @RequestMapping("/logout")
    public R logout() {
        return R.ok(null);
    }

    @RequestMapping("/refresh")
    public R refreshJwt(@RequestHeader("Authorization") String token) {
        Claims claims = jwtUtil.getClaimsFromHeader(token);
        String newToken = jwtUtil.generateToken(claims, Role.User);
        return R.ok(newToken);
    }
}