package com.example.cakezip.domain.cake

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.domain.shop.Shop
import lombok.*
import javax.persistence.*

@Entity
class CakeOptionList(

    @ManyToOne
    @JoinColumn(name = "shop_id")
    val shopId: Shop,

    @Enumerated(value = EnumType.STRING)
    val optionTitle: OptionTitleType,

    var optionDetail: String,

    val optionPrice: Long,

    ) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cakeOptionListId: Long? = null
    }
