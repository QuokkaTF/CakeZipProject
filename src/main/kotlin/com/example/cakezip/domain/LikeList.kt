package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.domain.shop.Shop
import org.jetbrains.annotations.NotNull
import lombok.*
import javax.persistence.*

@Entity
class LikeList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI 한다는 뜻
    val likeListId: Long ?= null,

    @ManyToOne
    @JoinColumn(name = "shop_id")
    val shop:Shop,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer:Customer,

    ) : BaseEntity() {}
