package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.shop.Shop
import lombok.*
import javax.persistence.*

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class OrderList : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_list_id")
    private val orderListId: Long? = null

    private val status: String?=null

    @OneToMany
    @JoinColumn(name = "order_id")
    private val order: Order?= null

    @OneToOne
    @JoinColumn(name = "cake_id")
    private val cake: Cake?= null

}
