package com.example.shop.admin;

import com.example.shop.sys.dto.ShopUser;
import com.example.shop.sys.service.UserService;
import com.example.shop.sys.vo.UpdateUserVO;
import com.example.shop.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/a/user")
public class UserAdminController {
    @Autowired
    private UserService userService;

    /**
     * 获取单个用户信息
     * @param id 用户ID
     * @return 固定响应体
     */
    @GetMapping("/get")
    public R getUser(@RequestParam("id") Integer id) {
        if (id == null) {
            return R.of(400, "用户ID为空", null);
        }
        return R.ok(userService.findUserSafelyById(id));
    }

    /**
     * 用户列表
     * @param page 页码
     * @param size 每页数量
     * @return 固定响应体
     */
    @GetMapping("/list")
    public R userList(@RequestParam(required = false) Integer page,
                      @RequestParam(required = false) Integer size) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 10;
        }
        // JPA分页从0开始；用户习惯分页从1开始，此处做了个-1转换。前端传入正常页数即可。
        return R.ok(userService.findUsersByPage(PageRequest.of(page - 1, size)));
    }

    /**
     * 管理员注册/添加
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

        ShopUser user = new ShopUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname == null ? "管理员" : nickname);
        userService.saveUser(user);
        return R.ok(null);
    }

    /**
     * 更新用户信息
     * @param vo 提供用户信息中的password、nickname数据
     * @return 固定响应体
     */
    @PostMapping("/update")
    public R updateUser(@RequestBody UpdateUserVO vo) {
        Integer id = vo.getId();
        if (id == null) {
            return R.of(400, "用户ID为空", null);
        }

        ShopUser user = userService.findUserById(id);
        if (vo.getPassword() != null) {
            user.setPassword(vo.getPassword());
        }
        if (vo.getNickname() != null) {
            user.setNickname(vo.getNickname());
        }
        userService.updateUser(user);
        return R.ok(null);
    }

    /**
     * 删除用户
     * @param params 用户的id参数
     * @return 固定响应体
     */
    @PostMapping("/delete")
    public R deleteUser(@RequestBody Map<String, Integer> params) {
        Integer id = params.get("id");
        return userService.deleteUser(id)
                ? R.ok(null)
                : R.of(400, "用户不存在", null);
    }
}
