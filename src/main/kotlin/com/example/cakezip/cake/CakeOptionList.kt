package com.example.cakezip.cake

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.shop.Shop
import javax.persistence.*

@Entity
class CakeOptionList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cakeOptionListId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "shop_id")
    val shopId: Shop,

    var optionDetail: String,

    val optionPrice: Long,

    val optionTitle: String,

    ) : BaseEntity() {}
