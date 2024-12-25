package com.example.shop.goods.service;

import com.example.shop.goods.dao.ShopCartRepository;
import com.example.shop.goods.dto.ShopCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private ShopCartRepository cartRepository;

    /**
     * 通过用户ID获取用户购物车列表
     * @param userId 用户ID
     * @return 购物车列表
     */
    public List<ShopCart> findByUserId(Integer userId) {
        List<ShopCart> list = cartRepository.findByUserId(userId);
        for (ShopCart s : list) {
            s.getUser().setPassword(null);
        }
        return list;
    }

    /**
     * 删除购物车选项
     * @param ids 选项ID
     * @param userId 用户ID
     * @return 结果
     */
    public boolean deleteCart(List<Integer> ids, Integer userId) {
        List<ShopCart> byUserId = cartRepository.findByUserId(userId);
        List<Integer> cartIdsByUserId = new ArrayList<>();
        byUserId.forEach(sc -> cartIdsByUserId.add(sc.getId()));
        List<Integer> finalList = ids.stream().filter(cartIdsByUserId::contains).collect(Collectors.toList());
        cartRepository.deleteAllById(finalList);
        return true;
    }

    /**
     * 添加购物车记录
     *
     * @param cart 购物车条目
     * @return 操作结果
     */
    public boolean addCart(ShopCart cart) {
        ShopCart log = cartRepository.findByUserIdAndGoodsId(cart.getUser().getId(), cart.getGoods().getId());
        if (log != null && log.getId() != null) {
            log.setGoodsNumber(log.getGoodsNumber() + 1);
            cart = log;
        }
        cartRepository.save(cart);
        return true;
    }

    /**
     * 修改购物车记录
     *
     * @param cart 购物车条目
     * @return 操作结果
     */
    public boolean saveCart(ShopCart cart) {
        Optional<ShopCart> byId = cartRepository.findById(cart.getId());
        if (byId.isPresent()) {
            if (cart.getGoodsNumber() < 1) {
                cartRepository.delete(cart);
            } else {
                cartRepository.save(cart);
            }
            return true;
        } else {
            return false;
        }
    }
}
