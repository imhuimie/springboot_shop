package com.example.shop.order.dao;

import com.example.shop.order.dto.ShopOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Integer> {
    Page<ShopOrder> findAllByUserId(Integer userId, PageRequest pageRequest);
}