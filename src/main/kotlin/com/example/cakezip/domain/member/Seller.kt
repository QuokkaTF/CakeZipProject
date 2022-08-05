package com.example.cakezip.domain.member

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.shop.Shop
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class Seller(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val sellerId: Long,

    @OneToOne
    @JoinColumn(name = "user_id")
    val user:User,

    @OneToOne
    @JoinColumn(name="shop_id")
    val shop: Shop,
) : BaseEntity() {}