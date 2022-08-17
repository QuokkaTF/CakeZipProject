package com.example.cakezip.domain

import com.example.cakezip.domain.cake.Cake
import lombok.*
import javax.persistence.*

@Entity
class OrderDetail : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderDetailId: Long? = null

    @OneToOne
    @JoinColumn(name = "order_id")
    var order: Orders?= null

    @OneToOne
    @JoinColumn(name = "cake_id")
    var cake: Cake?= null
}
