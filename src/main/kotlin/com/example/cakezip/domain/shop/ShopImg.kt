package com.example.cakezip.domain.shop

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Seller
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class ShopImg(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val shop_img_id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "shop_id")
    val shop: Shop,

    var shop_img_url: String,
    ) : BaseEntity() {}
