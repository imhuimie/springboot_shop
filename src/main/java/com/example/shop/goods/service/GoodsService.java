package com.example.shop.goods.service;

import com.example.shop.goods.dao.ShopGoodRepository;
import com.example.shop.goods.dto.ShopGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private ShopGoodRepository goodRepository;

    /**
     * 单个商品信息
     * @param id 商品ID
     * @return 商品信息
     */
    public ShopGood findGoodsById(int id) {
        return goodRepository.findById(id).orElse(null);
    }

    /**
     * 通过名称查找商品
     * @param name 商品名称
     * @return 商品信息
     */
    public ShopGood findGoodsByName(String name) {
        return goodRepository.findShopGoodByName(name);
    }

    public List<ShopGood> findGoodsByPage(PageRequest pageRequest) {
        return goodRepository.findAll(pageRequest).getContent();
    }

    public List<ShopGood> findGoodsByPageAndColor(String color, PageRequest pageRequest) {
        return goodRepository.findAllByColor(color, pageRequest).getContent();
    }

    public List<ShopGood> findGoodsByPageAndBrand(String brand, PageRequest pageRequest) {
        return goodRepository.findAllByBrand(brand, pageRequest).getContent();
    }

    public List<ShopGood> findGoodsByPageAndColorAndBrand(String color, String brand, PageRequest pageRequest) {
        return goodRepository.findAllByColorAndBrand(color, brand, pageRequest).getContent();
    }

    /**
     * 添加商品
     * @param good 商品信息
     * @return 结果
     */
    public boolean saveGood(ShopGood good) {
        good.setSales(0);  // 设置销量为0
        good.setState(1);  // 设置商品状态为1（上架）
        goodRepository.save(good);
        return true;
    }

    /**
     * 修改商品
     * @param good 商品信息
     * @return 结果
     */
    public boolean updateGood(ShopGood good) {
        goodRepository.save(good);
        return true;
    }

    /**
     * 删除商品
     * @param id 商品ID
     * @return 结果
     */
    public boolean deleteGood(int id) {
        goodRepository.deleteById(id);
        return true;
    }

    /**
     * 商品上下架
     * @param good 商品信息
     * @return 结果
     */
    public boolean changeGoodState(ShopGood good) {
        ShopGood g = findGoodsById(good.getId());
        g.setState(good.getState());
        goodRepository.save(g);
        return true;
    }
}
