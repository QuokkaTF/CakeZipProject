package com.example.cakezip.domain

import lombok.*
import javax.persistence.*

@Entity
@Builder
class Orders : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val orderId: Long? = null

    @Column(name = "merchant_uid")
    val merchantUid: Long? = null

    @Column(name = "merchant_price")
    var merchantPrice: Long? = null



}
