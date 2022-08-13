package com.example.cakezip.domain.cake

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.domain.shop.Shop
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class Cake(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cakeId:Long ?= null,

    var pickupDate: String,

    var letterText: String,

    var etc: String,

    var totalPrice: Int,

    var cakeStatus: String,

    @ManyToOne
    @JoinColumn(name = "shop_id")
    val shop: Shop,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: Customer,

    ) : BaseEntity() {}
