package com.example.cakezip.domain

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
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

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer : Customer? = null


}
