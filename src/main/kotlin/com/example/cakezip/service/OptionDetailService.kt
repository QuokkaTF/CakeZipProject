package com.example.cakezip.service

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.OptionTitleType
import com.example.cakezip.dto.EditOptionDto
import com.example.cakezip.dto.NewOptionReqDto

interface OptionDetailService {
    fun getOptionDetailByShopAndTypeAndStatus(shopId:Long, type:OptionTitleType, status:String) : List<CakeOptionList>
    fun addNewOption(newOptionReqDto: NewOptionReqDto)
    fun findByCakeOptionListId(optionId:Long) : CakeOptionList
    fun editCakeOption(optionId: Long, editOptionDto: EditOptionDto) : CakeOptionList
    fun deleteCakeOption(optionId: Long) : CakeOptionList
}
