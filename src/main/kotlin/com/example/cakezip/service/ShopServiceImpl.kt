package com.example.cakezip.service

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.OptionTitleType
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.NewShopReqDto
import com.example.cakezip.repository.CakeOptionListRepository
import com.example.cakezip.repository.ShopRepository
import org.springframework.stereotype.Service

@Service
class ShopServiceImpl(private val shopRepository: ShopRepository, private val cakeOptionListRepository: CakeOptionListRepository) : ShopService {

    override fun addNewShop(newShopReqDto: NewShopReqDto) {
        val shop : Shop = Shop(newShopReqDto.storeName, newShopReqDto.bussinessNum, newShopReqDto.storeNum, newShopReqDto.storeAddress,newShopReqDto.storeAddress,newShopReqDto.storeShortDescription)

        var newShop : Shop = shopRepository.save(shop)

        var optionDesignList : List<String> = newShopReqDto.designList.trim().split("  ")
        for(design in optionDesignList) {
            var info: List<String> = design.split(" ");
            var detail: String = info.get(0)
            var price: Long = info.get(1).replace("원", "").toLong()
            val cakeOptionList: CakeOptionList = CakeOptionList(newShop, OptionTitleType.DESIGN, detail, price);
            cakeOptionListRepository.save(cakeOptionList)
        }

        var optionSizeList : List<String> = newShopReqDto.sizeList.trim().split("  ")
        for(size in optionSizeList) {
            var info: List<String> = size.split(" ");
            var detail: String = info.get(0)
            var price: Long = info.get(1).replace("원", "").toLong()
            val cakeOptionList: CakeOptionList = CakeOptionList(newShop, OptionTitleType.SIZE, detail, price);
            cakeOptionListRepository.save(cakeOptionList)
        }

        var optionSheetList : List<String> = newShopReqDto.sheetList.trim().split("  ")
        for(sheet in optionSheetList) {
            var info: List<String> = sheet.split(" ");
            var detail: String = info.get(0)
            var price: Long = info.get(1).replace("원", "").toLong()
            val cakeOptionList: CakeOptionList = CakeOptionList(newShop, OptionTitleType.SFLAVOR, detail, price);
            cakeOptionListRepository.save(cakeOptionList)
        }

        var optionCreamList : List<String> = newShopReqDto.creamList.trim().split("  ")
        for(cream in optionCreamList) {
            var info: List<String> = cream.split(" ");
            var detail: String = info.get(0)
            var price: Long = info.get(1).replace("원", "").toLong()
            val cakeOptionList: CakeOptionList = CakeOptionList(newShop, OptionTitleType.CFLAVOR, detail, price);
            cakeOptionListRepository.save(cakeOptionList)
        }

        var optionCreamColorList : List<String> = newShopReqDto.creamColorList.trim().split("  ")
        for(creamColor in optionCreamColorList) {
            var info: List<String> = creamColor.split(" ");
            var detail: String = info.get(0)
            var price: Long = info.get(1).replace("원", "").toLong()
            val cakeOptionList: CakeOptionList = CakeOptionList(newShop, OptionTitleType.CCOLOR, detail, price);
            cakeOptionListRepository.save(cakeOptionList)
        }

        var optionLetterList : List<String> = newShopReqDto.letterList.trim().split("  ")
        for(letter in optionLetterList) {
            var info: List<String> = letter.split(" ");
            var detail: String = info.get(0)
            var price: Long = info.get(1).replace("원", "").toLong()
            val cakeOptionList: CakeOptionList = CakeOptionList(newShop, OptionTitleType.LCOLOR, detail, price);
            cakeOptionListRepository.save(cakeOptionList)
        }

        println("저장완료~")
    }
}