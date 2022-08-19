package com.example.cakezip.domain

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import lombok.*
import javax.persistence.*

@Entity
@Builder
class Orders(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long? = null,

    val merchantUid: String? = null,
    var merchantPrice: Long? = null,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer : Customer? = null,

    @OneToOne
    @JoinColumn(name = "cake_id")
    val cake: Cake?= null,
) : BaseEntity() {}
