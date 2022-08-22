package com.example.cakezip.dto

data class NewOptionReqDto (
    val shopId : Long = 0,
    val optionType : String = "",
    val optionDetail : String = "",
    val optionPrice : Int = 0,
        ) {}