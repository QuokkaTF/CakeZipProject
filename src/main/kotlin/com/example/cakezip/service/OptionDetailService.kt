package com.example.cakezip.service

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.OptionTitleType
import com.example.cakezip.dto.NewOptionReqDto

interface OptionDetailService {
    fun getOptionDetailByShopAndType(shopId:Long, type:OptionTitleType) : List<CakeOptionList>
    fun addNewOption(newOptionReqDto: NewOptionReqDto)
}