package com.example.shop.order.dao;

import com.example.shop.order.dto.ShopOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopOrderDetailRepository extends JpaRepository<ShopOrderDetail, Integer> {
    List<ShopOrderDetail> findAllByOrderId(Integer id);
}