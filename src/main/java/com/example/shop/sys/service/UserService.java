package com.example.shop.sys.service;

import com.example.shop.sys.dao.ShopUserRepository;
import com.example.shop.sys.dto.ShopUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private ShopUserRepository userRepository;

    /**
     * 添加用户
     * @param user 用户对象
     * @return 添加结果
     */
    public boolean saveUser(ShopUser user) {
        user.setState(1);
        userRepository.save(user);
        return true;
    }

    /**
     * 批量添加用户
     * @param users 用户列表
     * @return 添加结果
     */
    public boolean saveUsers(List<ShopUser> users) {
        for (ShopUser user : users) {
            user.setPassword("123456");
            user.setState(1);
        }
        userRepository.saveAll(users);
        return true;
    }

    /**
     * 删除用户
     * <p>
     *     考虑到用户可能与购物车、订单关联，不能真的把用户删掉。
     *     故此删除逻辑是将其状态位置0。状态字段含义如下：0-异常，1-正常。
     * </p>
     *
     * @param id 用户ID
     * @return 默认成功
     */
    public boolean deleteUser(int id) {
        ShopUser user = userRepository.findById(id).orElse(new ShopUser());
        if (user.getUsername() == null) {
            return false;
        }
        // todo 删除用户登录状态缓存
        userRepository.deleteById(id);
        return true;
    }

    /**
     * 批量删除用户
     * <p>
     *     将符合条件的用户状态置0.
     * </p>
     * @param ids 用户ID列表
     * @return 操作结果
     */
    public boolean deleteUsers(List<Integer> ids) {
        List<ShopUser> users = userRepository.findAllById(ids);
        if (users.isEmpty()) {
            return false;
        }
        for (ShopUser user : users) {
            user.setState(0);
        }
        userRepository.saveAll(users);
        return true;
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 修改后的用户信息
     */
    public ShopUser updateUser(ShopUser user) {
        return userRepository.save(user);
    }

    /**
     * 查找用户
     * @param id 用户ID
     * @return 用户信息
     */
    public ShopUser findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * 查找用户，不带密码数据
     * @param id 用户ID
     * @return 用户信息
     */
    public ShopUser findUserSafelyById(int id) {
        ShopUser user = userRepository.findById(id).orElse(null);
        if (user != null) {
            //不返回登录密码
            user.setPassword(null);
        }
        return user;
    }

    /**
     * 所有用户列表
     * @return 用户列表
     */
    public List<ShopUser> findAllUsers() {
        List<ShopUser> all = userRepository.findAll();
        for (ShopUser u : all) {
            u.setPassword(null);
        }
        return all;
    }

    /**
     * 分页读取所有用户列表
     * <p>
     *     需要注意的是,JPA分页从第0页开始
     * </p>
     * @param pageable 分页器
     * @return 用户列表
     */
    public List<ShopUser> findUsersByPage(Pageable pageable) {
        List<ShopUser> users = userRepository.findAll(pageable).getContent();
        for (ShopUser u : users) {
            u.setPassword(null);
        }
        return users;
    }

    /**
     * 通过登录账号查找用户
     * @param username 登录账号
     * @return 用户信息
     */
    public ShopUser findUserByUsername(String username) {
        List<ShopUser> users = userRepository.findShopUserByUsername(username);
        if (users.isEmpty()) {
            return null;
        }
        ShopUser user = users.get(0);
        user.setPassword(null);
        return user;
    }
}
