package com.example.cakezip.service

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.OptionTitleType
import com.example.cakezip.domain.cake.OptionTitleType.*
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.EditOptionDto
import com.example.cakezip.dto.NewOptionReqDto
import com.example.cakezip.repository.CakeOptionListRepository
import com.example.cakezip.repository.ShopRepository
import org.springframework.stereotype.Service

@Service
class OptionDetailServiceImpl(
    private val shopRepository: ShopRepository,
    private val cakeOptionListRepository: CakeOptionListRepository
    ) : OptionDetailService {

    override fun getOptionDetailByShopAndTypeAndStatus(shopId: Long, type: OptionTitleType, status:String)  : List<CakeOptionList>{
        var shop:Shop = shopRepository.findByShopId(shopId)
        var detailList : List<CakeOptionList> = cakeOptionListRepository.findByShopIdAndOptionTitleAndStatusAndOptionDetailNot(shop, type, status,"선택없음")
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

    override fun findByCakeOptionListId(optionId: Long): CakeOptionList{
        return cakeOptionListRepository.findByCakeOptionListId(optionId).get()
    }
    override fun editCakeOption(optionId: Long,editOptionDto: EditOptionDto): CakeOptionList {
        val editOption : CakeOptionList = cakeOptionListRepository.findByCakeOptionListId(optionId).get()
        editOption.optionDetail = editOptionDto.optionDetail
        editOption.optionPrice = editOptionDto.optionPrice
        cakeOptionListRepository.save(editOption)
        return editOption
    }

    override fun deleteCakeOption(optionId: Long): CakeOptionList {
        var deleteOption = cakeOptionListRepository.findByCakeOptionListId(optionId).get()
        deleteOption.status = "inactive"
        cakeOptionListRepository.save(deleteOption)
        return deleteOption
    }

}