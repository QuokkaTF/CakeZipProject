package com.example.cakezip.dto

import com.example.cakezip.domain.cake.Cake
import java.time.LocalDateTime

data class ReviewDto(
    //val reviewId: Long? = 0,
    val reviewTitle: String? = "",
    val reviewContent: String? = "",
    val reviewScore: Int? = 0,
    val shopName: String? = "", //샵
    val createdAt: LocalDateTime?
    //val cake: Cake?,

) {

}