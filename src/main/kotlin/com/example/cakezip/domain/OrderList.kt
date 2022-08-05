package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.shop.Shop
import lombok.*
import javax.persistence.*

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
class OrderList : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_list_id")
    val orderListId: Long? = null


    @ManyToOne
    @JoinColumn(name = "order_id")
    val order: Orders?= null

    @ManyToOne
    @JoinColumn(name = "cake_id")
    val cake: Cake?= null

}
