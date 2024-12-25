package com.example.shop.goods.dao;

import com.example.shop.goods.dto.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopCartRepository extends JpaRepository<ShopCart, Integer> {
    List<ShopCart> findByUserId(Integer userId);

    ShopCart findByUserIdAndGoodsId(Integer id, Integer id1);
}