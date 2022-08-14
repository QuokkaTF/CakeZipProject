package com.example.cakezip.dto

data class ShopSimpleInfoDto (
    val shopName : String = "",
    val shopShortDescription : String = "",
    val shopArea : String = "",
    val shopPhoneNum : String = "",
    val shopImgList : ArrayList<String> = ArrayList()
    ){}