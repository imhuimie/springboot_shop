package com.example.shop.admin;

import com.example.shop.order.dto.ShopOrder;
import com.example.shop.order.service.OrderService;
import com.example.shop.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/a/order")
public class OrderAdminController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public R list(@RequestParam(required = false) Integer page,
                  @RequestParam(required = false) Integer size) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 10;
        }
        List<ShopOrder> orders = orderService.orderListAll(PageRequest.of(page-1, size));
        return R.ok(orders);
    }


}
