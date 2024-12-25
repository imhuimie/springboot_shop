package com.example.shop.goods.vo;

import lombok.Data;

@Data
public class CartVO {
    private Integer id;
    private Integer goodsNumber;
    private Integer goodsId;
    private Integer userId;
}
