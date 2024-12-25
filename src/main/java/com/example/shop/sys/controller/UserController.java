package com.example.shop.sys.controller;

import com.example.shop.sys.dto.ShopUser;
import com.example.shop.sys.service.UserService;
import com.example.shop.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param params 注册所需的username、password、confirmPassword参数
     * @return 固定响应体
     */
    @PostMapping("/add")
    public R register(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String nickname = params.get("nickname");
        String confirmPassword = params.get("confirmPassword");
        if (username == null || password == null || confirmPassword == null) {
            return R.of(400, "参数不全", null);
        }
        if (!password.equals(confirmPassword)) {
            return R.of(400, "两次密码不一致", null);
        }

        ShopUser u = userService.findUserByUsername(username);
        if (u != null) {
            return R.of(400, "用户已存在", null);
        }

        ShopUser user = new ShopUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname == null ? "系统用户" : nickname);
        userService.saveUser(user);
        return R.ok(null);
    }
}
