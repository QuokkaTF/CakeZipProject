package com.example.cakezip.dto

import com.example.cakezip.domain.cake.Cake

data class OrderDto(
    val orderId: Long?, //오더디테일
    val customerName: String?, // 유저
    val shopName: String?, //샵
    val cake: Cake?,

) {

}