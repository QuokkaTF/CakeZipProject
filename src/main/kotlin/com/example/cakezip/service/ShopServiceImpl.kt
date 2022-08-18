package com.example.cakezip.service

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.NewShopReqDto
import com.example.cakezip.repository.ShopRepository
import org.springframework.stereotype.Service

@Service
class ShopServiceImpl(private val shopRepository: ShopRepository) : ShopService {

    override fun findBySeller(seller:Seller) : Shop {
        var shop = shopRepository.findBySeller(seller)
        return shop
    }

    override fun addNewShop(newShopReqDto: NewShopReqDto) {
        val shop : Shop = Shop(newShopReqDto.storeName, newShopReqDto.bussinessNum, newShopReqDto.storeNum, newShopReqDto.storeAddress,newShopReqDto.storeAddress,newShopReqDto.storeShortDescription)

        var newShop : Shop = shopRepository.save(shop)

        var optionDesignList : List<String> = newShopReqDto.designList.trim().split("  ")
        for(design in optionDesignList) {
            //val cakeOptionList : CakeOptionList = CakeOptionList(newShop,"케이크디자인", design,1000);
        }
        var optionSizeList : List<String> = newShopReqDto.sizeList.trim().split("  ")
        var optionSheetList : List<String> = newShopReqDto.sheetList.trim().split("  ")
        var optionCreamList : List<String> = newShopReqDto.creamList.trim().split("  ")
        var optionCreamColorList : List<String> = newShopReqDto.creamColorList.trim().split("  ")
        var optionLetterList : List<String> = newShopReqDto.letterList.trim().split("  ")

        println("저장완료~")
    }

    override fun searchShop(keyword:String):List<Shop> {
        var shopList = shopRepository.findByShopNameContaining(keyword)
        return shopList
    }
}