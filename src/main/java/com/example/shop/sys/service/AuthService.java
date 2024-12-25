package com.example.shop.sys.service;

import com.example.shop.sys.dao.ShopUserRepository;
import com.example.shop.sys.dto.ShopUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    private ShopUserRepository userRepository;

    /**
     * 用户登录
     * @param username 账号
     * @param password 密码
     * @return 用户信息
     */
    public ShopUser login(String username, String password) {
        List<ShopUser> users = userRepository.findByUsernameAndPassword(username, password);
        if (!users.isEmpty() && users.get(0).getState() == 1) {
            return users.get(0);
        }
        return null;
    }

    /**
     * 用户注销登录
     * @param username 账号
     * @return 注销结果
     */
    public boolean logout(String username) {
        return true;
    }

    /**
     * 用户注册
     * @param username 账号
     * @param password 密码
     * @return 注册结果
     */
    public boolean register(String username, String password) {
        List<ShopUser> userByUsername = userRepository.findByUsername(username);
        if (!userByUsername.isEmpty()) {
            // 登录账号已存在
            return false;
        }
        ShopUser user = new ShopUser();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }
}