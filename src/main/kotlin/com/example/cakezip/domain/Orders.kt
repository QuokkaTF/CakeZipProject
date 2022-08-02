package com.example.cakezip.domain

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
    @NonNull
    private val orderId: Long = 0

    @Column(name = "merchant_uid")
    private val merchantUid: Int? = null

    @Column(name = "merchant_price")
    private val merchantPrice: Int? = null

    private val status: String?=null

}
