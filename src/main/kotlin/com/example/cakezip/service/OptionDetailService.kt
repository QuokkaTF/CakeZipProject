package com.example.cakezip.service

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.OptionTitleType
import com.example.cakezip.dto.NewOptionReqDto

interface OptionDetailService {
    fun getOptionDetailByShopAndTypeAndStatus(shopId:Long, type:OptionTitleType, status:String) : List<CakeOptionList>
    fun addNewOption(newOptionReqDto: NewOptionReqDto)
}