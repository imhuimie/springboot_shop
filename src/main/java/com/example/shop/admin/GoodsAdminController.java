package com.example.shop.admin;

import com.example.shop.goods.dto.ShopGood;
import com.example.shop.goods.service.GoodsService;
import com.example.shop.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/a/goods")
public class GoodsAdminController {
    @Autowired
    private GoodsService goodsService;

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
                       @RequestParam(required = false) String sort) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 10;
        }
        Sort s = Sort.by("id").descending();
        if (sort != null) {
            s = Sort.by(sort).descending();
        }
        return R.ok(goodsService.findGoodsByPage(PageRequest.of(page - 1, size, s)));
    }

    /**
     * 商品信息
     *
     * @param id 商品ID
     * @return 固定响应体
     */
    @GetMapping("/get")
    public R getGoods(@RequestParam Integer id) {
        if (id == null) {
            return R.of(400, "商品ID为空", null);
        }
        return R.ok(goodsService.findGoodsById(id));
    }

    /**
     * 添加商品
     *
     * @param goods 商品信息
     * @return 固定响应体
     */
    @PostMapping("/add")
    public R addGoods(@RequestBody ShopGood goods) {
        if (goods == null) {
            return R.of(400, "参数不全", null);
        }
        goodsService.saveGood(goods);
        return R.ok(null);
    }

    /**
     * 更新商品
     *
     * @param goods 商品信息
     * @return 固定响应体
     */
    @PostMapping("/update")
    public R updateGoods(@RequestBody ShopGood goods) {
        if (goods == null) {
            return R.of(400, "参数不全", null);
        }
        goodsService.updateGood(goods);
        return R.ok(null);
    }

    /**
     * 删除商品
     *
     * @param good 商品信息，只需要传ID
     * @return 固定响应体
     */
    @PostMapping("/delete")
    public R deleteGoods(@RequestBody ShopGood good) {
        if (good == null || good.getId() == null) {
            return R.of(400, "未指定商品", null);
        }
        goodsService.deleteGood(good.getId());
        return R.ok(null);
    }

    /**
     * 商品上下架操作
     * @param goods 提供商品id和state上下架信息。1-上架，0-下架
     * @return 固定响应体
     */
    @PostMapping("/change")
    public R changeGoodsState(@RequestBody ShopGood goods) {
        if (goods == null) {
            return R.of(400, "参数不全", null);
        }
        goodsService.changeGoodState(goods);
        return R.ok(null);
    }
}
