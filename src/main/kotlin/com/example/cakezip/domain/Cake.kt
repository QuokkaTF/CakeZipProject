package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.shop.Shop
import lombok.*
import javax.persistence.*

@Entity
class Cake : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private val cakeId: Long? = null

    private val pickupDate: String? = null

    private val cakeStatus: String? = null

    private val status: String?=null

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private val shop: Shop?= null

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private val customer: Customer?= null

}
