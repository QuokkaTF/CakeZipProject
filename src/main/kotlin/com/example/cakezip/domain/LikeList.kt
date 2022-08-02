package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import org.jetbrains.annotations.NotNull
import lombok.*
import javax.persistence.*

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class LikeList : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI 한다는 뜻
    @Column(name = "like_list_id")
    private val likeListId: Long ?= null

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private val shop:Shop ?= null

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private val customer:Customer ?= null

    private val status: String? = null


}