package com.example.cakezip.dto

import java.time.LocalDateTime

data class ReviewDto(

    val reviewTitle: String?,
    val reviewContent: String?,
    val reviewScore: Int?,
    val shopName: String?,
    val createdAt: LocalDateTime?,
    val cake: Cake,

) {
}
