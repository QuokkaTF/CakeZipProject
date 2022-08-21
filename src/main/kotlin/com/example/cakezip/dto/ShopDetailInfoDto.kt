package com.example.cakezip.dto

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.member.Seller

data class ShopDetailInfoDto (
    val shopID : Long?,
    val shopName : String = "",
    val shopAddress : String = "",
    val shopArea : String = "",
    val shopShortDescription : String = "",
    val shopPhoneNum : String = "",
    val shopOwner : Seller?,
    val shopDescriptionImgUrl : String = "",
    val shopImgList : ArrayList<String> = ArrayList(),
    val designOptionList : ArrayList<CakeOptionList> = ArrayList(),
    val sizeOptionList : ArrayList<CakeOptionList> = ArrayList(),
    val sheetOptionList : ArrayList<CakeOptionList> = ArrayList(),
    val creamOptionList : ArrayList<CakeOptionList> = ArrayList(),
    val creamColorOptionList : ArrayList<CakeOptionList> = ArrayList(),
    val letterOptionList : ArrayList<CakeOptionList> = ArrayList(),
    val shopLikeCount : Int = 0,
    val likeCheck : Boolean ?= false,
){}