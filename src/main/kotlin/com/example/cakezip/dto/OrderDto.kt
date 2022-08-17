package com.example.cakezip.dto

import com.example.cakezip.domain.cake.Cake

data class OrderDto(
    val orderId: Long?, //오더디테일
    val customerName: String?, // 유저
    //val totalPrice: Int = 0, //케이크
    val shopName: String?, //샵
    //val pickupDate: String? = "", //케이크
    //val status: String = "",
    val cake: Cake?,

) {

}