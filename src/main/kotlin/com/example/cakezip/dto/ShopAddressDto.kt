package com.example.cakezip.dto

import com.example.cakezip.domain.shop.Shop

data class ShopAddressDto(
    var shopId:Long?,
    var shopAddress: String,
    var shopName: String,
    var shopLink: String = "www.naver.com"
) {

}