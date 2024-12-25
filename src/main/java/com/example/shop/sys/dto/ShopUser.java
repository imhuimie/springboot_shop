package com.example.shop.sys.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "shop_user", schema = "shop")
public class ShopUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_name", nullable = false, length = 50)
    private String username;

    @Column(name = "user_password", nullable = false, length = 50)
    private String password;

    @ColumnDefault("'系统用户'")
    @Column(name = "user_nickname", length = 20)
    private String nickname;

    @ColumnDefault("1")
    @Column(name = "user_state")
    private Integer state;

}