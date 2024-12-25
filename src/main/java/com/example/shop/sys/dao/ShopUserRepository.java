package com.example.shop.sys.dao;

import com.example.shop.goods.dto.ShopGood;
import com.example.shop.sys.dto.ShopUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopUserRepository extends JpaRepository<ShopUser, Integer> {
    List<ShopUser> findByUsernameAndPassword(String username, String password);

    List<ShopUser> findByUsername(String username);

    List<ShopUser> findShopUserByUsername(String username);
}