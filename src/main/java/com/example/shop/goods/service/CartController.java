package com.example.shop.goods.service;

import com.example.shop.goods.dto.ShopCart;
import com.example.shop.goods.dto.ShopGood;
import com.example.shop.goods.vo.CartVO;
import com.example.shop.sys.dto.ShopUser;
import com.example.shop.util.JwtUtil;
import com.example.shop.util.R;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户购物车列表
     *
     * @param token 用户登录信息
     * @return 固定响应体
     */
    @GetMapping("/list")
    public R list(@RequestHeader("Authorization") String token) {
        Claims claims = jwtUtil.getClaimsFromHeader(token);
        List<ShopCart> carts = cartService.findByUserId(claims.get("id", Integer.class));
        return R.ok(carts);
    }

    /**
     * 删除购物车信息
     *
     * @param ids 条目ID（批量）
     * @return 固定响应体
     */
    @PostMapping("/delete")
    public R delete(@RequestBody List<Integer> ids, @RequestHeader("Authorization") String token) {
        if (ids == null || ids.isEmpty()) {
            return R.of(400, "参数不正确", null);
        }
        cartService.deleteCart(ids, jwtUtil.getClaimsFromHeader(token).get("id", Integer.class));
        return R.ok(null);
    }

    /**
     * 添加购物车条目
     * @param vo 提供goodsId信息
     * @param token 请求头的Token信息
     * @return 固定响应体
     */
    @PostMapping("/add")
    public R add(@RequestBody CartVO vo, @RequestHeader("Authorization") String token) {
        if (vo == null || vo.getGoodsId() == null) {
            return R.of(400, "参数有误", null);
        }

        ShopCart cart = new ShopCart();
        cart.setGoodsNumber(1);
        ShopGood goods = new ShopGood();
        goods.setId(vo.getGoodsId());
        cart.setGoods(goods);

        ShopUser user = new ShopUser();
        user.setId(jwtUtil.getClaimsFromHeader(token).get("id", Integer.class));
        cart.setUser(user);

        cartService.addCart(cart);
        return R.ok(null);
    }

    /**
     * 修改购物车条目信息
     * @param vo 条目概要信息
     * @param token 请求头Token信息
     * @return 固定响应体
     */
    @PostMapping("/update")
    public R update(@RequestBody CartVO vo, @RequestHeader("Authorization") String token) {
        if (vo == null) {
            return R.of(400, "参数不正确", null);
        }
        ShopCart cart = new ShopCart();
        cart.setId(vo.getId());
        cart.setGoodsNumber(vo.getGoodsNumber());

        ShopGood goods = new ShopGood();
        goods.setId(vo.getGoodsId());
        cart.setGoods(goods);

        ShopUser user = new ShopUser();
        user.setId(jwtUtil.getClaimsFromHeader(token).get("id", Integer.class));
        cart.setUser(user);

        return cartService.saveCart(cart)
                ? R.ok(null)
                : R.of(400, "未能成功", null);
    }
}
