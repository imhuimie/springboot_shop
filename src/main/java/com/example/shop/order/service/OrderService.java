package com.example.shop.order.service;

import com.example.shop.goods.dao.ShopCartRepository;
import com.example.shop.goods.dao.ShopGoodRepository;
import com.example.shop.goods.dto.ShopCart;
import com.example.shop.goods.dto.ShopGood;
import com.example.shop.order.dao.ShopOrderDetailRepository;
import com.example.shop.order.dao.ShopOrderRepository;
import com.example.shop.order.dto.ShopOrder;
import com.example.shop.order.dto.ShopOrderDetail;
import com.example.shop.sys.dto.ShopUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private ShopOrderRepository orderRepository;
    @Autowired
    private ShopOrderDetailRepository orderDetailRepository;
    @Autowired
    private ShopGoodRepository goodRepository;
    @Autowired
    private ShopCartRepository cartRepository;

    /**
     * 将购物车内容添加至订单
     * @param userId 用户ID
     * @param _cartIds 购物车条目ID列表
     * @return 结果
     */
    public boolean addOrder(Integer userId, Iterable<Integer> _cartIds) {
        // 筛选指定用户关联的购物车条目
        List<ShopCart> logCarts = cartRepository.findAllById(_cartIds);
        List<ShopCart> carts = logCarts.stream().filter(c -> userId.equals(c.getUser().getId())).toList();
        if (carts.isEmpty()) {return false;}
        // 计算总价
        double totalPrice = carts.stream().mapToDouble(c -> c.getGoodsNumber() * c.getGoods().getCost()).sum();
        ShopUser user = new ShopUser();
        user.setId(userId);
        // 保存订单信息
        ShopOrder order = new ShopOrder();
        order.setUser(user);
        order.setCost(totalPrice);
        order.setState(1);
        order.setCreateTime(Instant.now());
        ShopOrder o = orderRepository.save(order);
        // 保存订单详情信息
        List<ShopOrderDetail> orderDetailList = new ArrayList<>(carts.size());
        for (ShopCart c : carts) {
            ShopOrderDetail orderDetail = new ShopOrderDetail();
            orderDetail.setOrder(o);
            orderDetail.setGoodsNumber(c.getGoodsNumber());
            orderDetail.setGoods(c.getGoods());
            orderDetailList.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetailList);
        // 更新销量
        List<Integer> goodIds = carts.stream().map(c -> c.getGoods().getId()).toList();
        List<Integer> goodNumbers = carts.stream().map(ShopCart::getGoodsNumber).toList();
        List<ShopGood> goodList = goodRepository.findAllById(goodIds);
        for (int i = 0; i < goodList.size(); i++) {
            ShopGood g = goodList.get(i);
            g.setSales(g.getSales() + goodNumbers.get(i));
        }
        goodRepository.saveAll(goodList);
        // 删除购物车记录
        cartRepository.deleteAll(carts);
        return true;
    }

    /**
     * 全部订单列表
     * @param pageRequest 分页器
     * @return 订单列表
     */
    public List<ShopOrder> orderListAll(PageRequest pageRequest) {
        List<ShopOrder> list = orderRepository.findAll(pageRequest).getContent();
        if (!list.isEmpty()) {
            for (ShopOrder o : list) {
                o.getUser().setPassword(null);
            }
        }
        return list;
    }

    /**
     * 根据用户ID查找订单列表
     * @param userId 用户ID
     * @param pageRequest 分页器
     * @return 订单列表
     */
    public List<ShopOrder> orderListByUserId(Integer userId, PageRequest pageRequest) {
        List<ShopOrder> list = orderRepository.findAllByUserId(userId, pageRequest).getContent();
        if (!list.isEmpty()) {
            for (ShopOrder o : list) {
                o.getUser().setPassword(null);
            }
        }
        return list;
    }

    /**
     * 订单详情
     * @param orderId 订单ID
     * @return 订单详情
     */
    public ShopOrder findOrderById(Integer orderId) {
        ShopOrder order = orderRepository.findById(orderId).orElse(new ShopOrder());
        if (order.getId() != null) {
            order.setOrderDetails(orderDetailRepository.findAllByOrderId(order.getId()));
        }
        return order;
    }

    public ShopOrder findOrderByIdAndUserId(Integer orderId, Integer userId) {
        ShopOrder order = orderRepository.findById(orderId).orElse(new ShopOrder());
        if (order.getId() != null && userId.equals(order.getUser().getId())) {
            List<ShopOrderDetail> details = orderDetailRepository.findAllByOrderId(order.getId());
            order.setOrderDetails(details);
            order.getUser().setPassword(null);
        }
        return order;
    }
}
