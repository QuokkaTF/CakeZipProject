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
class Order : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private val cakeId: Int? = null

    @Column(name = "merchant_uid")
    private val merchantUid: Int? = null

    @Column(name = "merchant_price")
    private val merchantPrice: Int? = null

    private val status: String?=null

}
