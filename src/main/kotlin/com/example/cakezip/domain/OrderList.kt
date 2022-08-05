package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.shop.Shop
import lombok.*
import javax.persistence.*

@Entity
class OrderList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderListId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "order_id")
    val order: Orders,

    @ManyToOne
    @JoinColumn(name = "cake_id")
    val cake: Cake,

    ) : BaseEntity() {}

