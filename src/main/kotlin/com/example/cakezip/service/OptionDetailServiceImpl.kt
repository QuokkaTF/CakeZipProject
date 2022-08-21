package com.example.cakezip.service

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.OptionTitleType
import com.example.cakezip.domain.cake.OptionTitleType.*
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.NewOptionReqDto
import com.example.cakezip.repository.CakeOptionListRepository
import com.example.cakezip.repository.ShopRepository
import org.springframework.stereotype.Service

@Service
class OptionDetailServiceImpl(private val shopRepository: ShopRepository, private val cakeOptionListRepository: CakeOptionListRepository) : OptionDetailService {

    override fun getOptionDetailByShopAndTypeAndStatus(shopId: Long, type: OptionTitleType, status:String)  : List<CakeOptionList>{
        var shop:Shop = shopRepository.findByShopId(shopId)
        var detailList : List<CakeOptionList> = cakeOptionListRepository.findByShopIdAndOptionTitleAndStatus(shop, type, status)
        return detailList
    }

    override fun addNewOption(newOptionReqDto: NewOptionReqDto) {
        var optionType:OptionTitleType = DESIGN

        when(newOptionReqDto.optionType) {
            "design" -> optionType = DESIGN
            "size" -> optionType = SIZE
            "sheet" -> optionType = SFLAVOR
            "cream" -> optionType = CFLAVOR
            "creamcolor" -> optionType = CCOLOR
            "letter" -> optionType = LCOLOR
        }
        cakeOptionListRepository.save(CakeOptionList(shopRepository.findByShopId(newOptionReqDto.shopId),optionType,newOptionReqDto.optionDetail, newOptionReqDto.optionPrice))
    }

}