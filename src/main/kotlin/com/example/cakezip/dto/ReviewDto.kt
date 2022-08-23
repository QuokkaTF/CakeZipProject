package com.example.cakezip.dto

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.cake.Cake
import java.time.LocalDateTime

data class ReviewDto(

    val reviewTitle: String?,
    val reviewContent: String?,
    val reviewScore: Int?,
    val shopName: String?,
    val createdAt: LocalDateTime?,
    val order: Orders?,
    val cake: Cake,

    ) {
}
