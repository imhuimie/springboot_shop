package com.example.shop.order.controller;


import com.example.shop.order.dto.ShopOrder;
import com.example.shop.order.service.OrderService;
import com.example.shop.util.JwtUtil;
import com.example.shop.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/list")
    public R list(@RequestParam(required = false) Integer page,
                  @RequestParam(required = false) Integer size,
                  @RequestHeader("Authorization") String token) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 10;
        }
        List<ShopOrder> orders = orderService.orderListByUserId(
                jwtUtil.getClaimsFromHeader(token).get("id", Integer.class),
                PageRequest.of(page - 1, size, Sort.Direction.DESC, "id")
        );
        return R.ok(orders);
    }

    @GetMapping("/get")
    public R get(@RequestParam(required = false) Integer id,
                 @RequestHeader("Authorization") String token) {
        if (id == null || id <= 0) {
            return R.of(400, "订单ID无效", null);
        }
        ShopOrder order = orderService.findOrderByIdAndUserId(
                id,
                jwtUtil.getClaimsFromHeader(token).get("id", Integer.class)
        );
        return R.ok(order);
    }

    @PostMapping("/add")
    public R add(@RequestBody List<Integer> cartIds,
                 @RequestHeader("Authorization") String token) {
        if (cartIds == null || cartIds.isEmpty()) {
            return R.of(400, "参数不全", null);
        }
        boolean ok = orderService.addOrder(
                jwtUtil.getClaimsFromHeader(token).get("id", Integer.class),
                cartIds
        );
        if (ok) {
            return R.ok(null);
        } else {
            return R.of(500, "下单失败", null);
        }
    }
}
