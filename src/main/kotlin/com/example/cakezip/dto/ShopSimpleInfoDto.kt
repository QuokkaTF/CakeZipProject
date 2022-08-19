package com.example.cakezip.dto

data class ShopSimpleInfoDto (
    val shopId : Long?,
    val shopName : String = "",
    val shopShortDescription : String = "",
    val shopArea : String = "",
    val shopImgList : ArrayList<String> = ArrayList()
    ){}