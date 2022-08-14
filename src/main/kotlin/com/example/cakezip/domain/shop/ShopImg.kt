package com.example.cakezip.domain.shop

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Seller
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class ShopImg(
    @ManyToOne
    @JoinColumn(name = "shop_id")
    val shop: Shop,

    var shopImgUrl: String,
    ) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val shopImgId: Long? = null
    }
