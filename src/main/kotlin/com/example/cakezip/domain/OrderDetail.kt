package com.example.cakezip.domain

import com.example.cakezip.domain.cake.Cake
import lombok.*
import javax.persistence.*

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
class OrderDetail : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    val orderDetailId: Long? = null


    @ManyToOne
    @JoinColumn(name = "order_id")
    val order: Orders?= null

    @ManyToOne
    @JoinColumn(name = "cake_id")
    val cake: Cake?= null

}
