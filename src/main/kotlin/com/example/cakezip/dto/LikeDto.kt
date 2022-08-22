package com.example.cakezip.dto

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop

data class LikeDto(
    val likeListId: Long,
    val shop: Shop,
    val customer: Customer,
) {
}
