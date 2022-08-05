package com.example.cakezip.domain

import lombok.*
import javax.persistence.*

@Entity
class Orders(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long? = null,

    val merchantUid: Long,
    val merchantPrice: Long,

    ) : BaseEntity() {}
