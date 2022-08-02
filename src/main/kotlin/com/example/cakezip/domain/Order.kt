package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import lombok.*
import javax.persistence.*

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Orders : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private val orderId: Long? = null

    @Column(name = "merchant_uid")
    private val merchantUid: Long? = null

    @Column(name = "merchant_price")
    private val merchantPrice: Long? = null

    private val status: String?=null

}
