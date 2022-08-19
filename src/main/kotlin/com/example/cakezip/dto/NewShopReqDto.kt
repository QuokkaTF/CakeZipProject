package com.example.cakezip.dto

import org.hibernate.internal.util.type.PrimitiveWrapperHelper.ShortDescriptor
import org.springframework.web.multipart.MultipartFile

data class NewShopReqDto (
    val storeName : String = "",
    val bussinessNum : String = "",
    val storeNum : String = "",
    val storeAddress : String = "",
    val storeDetailAddress : String = "",
    val storeShortDescription : String = "",
    val storeImgList : ArrayList<String> = ArrayList(),
    val storeDetailImg : String = "",
    val designList : String = "",
    val sizeList : String = "",
    val sheetList : String = "",
    val creamList : String = "",
    val creamColorList : String = "",
    val letterList : String = "",
) {}