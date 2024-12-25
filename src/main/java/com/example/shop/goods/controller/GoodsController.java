package com.example.shop.goods.controller;

import com.example.shop.goods.dto.ShopGood;
import com.example.shop.goods.service.GoodsService;
import com.example.shop.util.R;
import com.example.shop.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 商品信息
     *
     * @param id 商品ID
     * @return 固定响应体
     */
    @GetMapping("/get")
    public R getGoods(@RequestParam(required = false) Integer id) {
        if (id == null) {
            return R.of(400, "商品ID为空", null);
        }
        return R.ok(goodsService.findGoodsById(id));
    }

    /**
     * 商品列表
     *
     * @param page （可选）页码，默认1
     * @param size （可选）每页数量，默认10
     * @param sort （可选）排序字段，可填sales、cost；填写表示倒序列表
     * @return 固定响应体
     */
    @GetMapping("/list")
    public R goodsList(@RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer size,
                       @RequestParam(required = false) String sort,
                       @RequestParam(required = false) String color,
                       @RequestParam(required = false) String brand) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 10;
        }
        Sort s = Sort.by("id").ascending();
        if (StringUtil.notBlank(sort) && List.of("sales", "cost").contains(sort)) {
            s = Sort.by(sort).descending();
        }

        List<ShopGood> list = null;

        if (StringUtil.isBlank(color) && StringUtil.isBlank(brand)) {
            list = goodsService.findGoodsByPage(PageRequest.of(page - 1, size, s));
        } else {
            if (StringUtil.notBlank(color) && StringUtil.notBlank(brand)) {
                list = goodsService.findGoodsByPageAndColorAndBrand(color, brand, PageRequest.of(page - 1, size, s));
            } else {
                if (StringUtil.notBlank(color)) {
                    list = goodsService.findGoodsByPageAndColor(color, PageRequest.of(page - 1, size, s));
                } else {
                    list = goodsService.findGoodsByPageAndBrand(brand, PageRequest.of(page - 1, size, s));
                }
            }
        }

        return R.ok(list);
    }
}
