package com.example.shop.goods.dto;

import com.example.shop.sys.dto.ShopUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "shop_cart", schema = "shop")
public class ShopCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ColumnDefault("1")
    @Column(name = "cart_goods_number", nullable = false)
    private Integer goodsNumber;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cart_goods_id", nullable = false)
    private ShopGood goods;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_user_id")
    private ShopUser user;

}