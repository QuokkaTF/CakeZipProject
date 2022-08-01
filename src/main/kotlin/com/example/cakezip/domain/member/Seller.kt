package com.example.cakezip.domain.member

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.shop.Shop
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Seller : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    @NotNull
    private val sellerId: Long = 0

    @OneToOne
    @JoinColumn(name = "user_id")
    private val user:User ?= null

    @OneToOne
    @JoinColumn(name="shop_id")
    private val shop: Shop?= null

    private val status: String?=null
}