package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.domain.shop.Shop
import lombok.*
import javax.persistence.*

@Entity
class CakeOptionList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cakeOptionListId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "shop_id")
    val shopId: Shop,

    @Column(name = "option_title")
    val optionTitle: String,

    @Column(name = "option_detail")
    val optionDetail: String,

    @Column(name = "option_price")
    val optionPrice: Long,

    @Enumerated(value = EnumType.STRING)
    var userType: UserType
    ) : BaseEntity() {}
