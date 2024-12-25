package com.example.shop.goods.dao;

import com.example.shop.goods.dto.ShopGood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopGoodRepository extends JpaRepository<ShopGood, Integer> {
    ShopGood findShopGoodByName(String name);
    Page<ShopGood> findAllByColor(String color, Pageable page);
    Page<ShopGood> findAllByBrand(String brand, Pageable page);
    Page<ShopGood> findAllByColorAndBrand(String color, String brand, Pageable page);
}