package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import lombok.*
import javax.persistence.*

@Entity
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val reviewId: Long? = null,
    
    val reviewTitle: String,
    val reviewContent: String,
    val reviewScore: Float,

    @OneToOne
    @JoinColumn(name = "cake_id")
    val cake: Cake,

    ) : BaseEntity() {}
