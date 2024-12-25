package com.example.shop.order.dto;

import com.example.shop.goods.dto.ShopGood;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "shop_order_detail", schema = "shop")
public class ShopOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private ShopOrder order;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "goods_id", nullable = false)
    private ShopGood goods;

    @ColumnDefault("1")
    @Column(name = "goods_number")
    private Integer goodsNumber;

}