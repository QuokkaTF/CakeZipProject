package com.example.cakezip.dto

import com.example.cakezip.domain.cake.Cake

data class OrderDto(
    val orderId: Long?,
    val customerName: String?,
    val shopName: String?,
    val cake: Cake?,
) {
}
