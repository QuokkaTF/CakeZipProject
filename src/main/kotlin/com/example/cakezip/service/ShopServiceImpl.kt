package com.example.cakezip.service

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.OptionTitleType
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.domain.shop.ShopImg
import com.example.cakezip.dto.NewShopReqDto
import com.example.cakezip.repository.CakeOptionListRepository
import com.example.cakezip.repository.ShopImgRepository
import com.example.cakezip.repository.ShopRepository
import org.springframework.stereotype.Service

@Service
class ShopServiceImpl(private val shopRepository: ShopRepository, private val cakeOptionListRepository: CakeOptionListRepository, private val shopImgRepository: ShopImgRepository) : ShopService {

    override fun addNewShop(newShopReqDto: NewShopReqDto) {
        val shop : Shop = Shop(newShopReqDto.storeName, newShopReqDto.bussinessNum, newShopReqDto.storeNum, newShopReqDto.storeAddress +" "+newShopReqDto.storeDetailAddress,newShopReqDto.storeDetailImg,newShopReqDto.storeShortDescription)

        var newShop : Shop = shopRepository.save(shop)

        for (imageUrl in newShopReqDto.storeImgList) {
            val shopImg = ShopImg(newShop,imageUrl)
            shopImgRepository.save(shopImg)
        }

        var optionDesignList : List<String> = newShopReqDto.designList.trim().split("  ")
        for(design in optionDesignList) {
            if(design.length==0) {
                break
            }
            var info: List<String> = design.split(" ");
            var detail: String = info.get(0)
            var price: Long = info.get(1).replace("원", "").toLong()
            val cakeOptionList: CakeOptionList = CakeOptionList(newShop, OptionTitleType.DESIGN, detail, price);
            cakeOptionListRepository.save(cakeOptionList)
        }

        var optionSizeList : List<String> = newShopReqDto.sizeList.trim().split("  ")
        for(size in optionSizeList) {
            if(size.length==0) {
                break
            }
            var info: List<String> = size.split(" ");
            var detail: String = info.get(0)
            var price: Long = info.get(1).replace("원", "").toLong()
            val cakeOptionList: CakeOptionList = CakeOptionList(newShop, OptionTitleType.SIZE, detail, price);
            cakeOptionListRepository.save(cakeOptionList)
        }

        var optionSheetList : List<String> = newShopReqDto.sheetList.trim().split("  ")
        for(sheet in optionSheetList) {
            if(sheet.length==0) {
                break
            }
            var info: List<String> = sheet.split(" ");
            var detail: String = info.get(0)
            var price: Long = info.get(1).replace("원", "").toLong()
            val cakeOptionList: CakeOptionList = CakeOptionList(newShop, OptionTitleType.SFLAVOR, detail, price);
            cakeOptionListRepository.save(cakeOptionList)
        }

        var optionCreamList : List<String> = newShopReqDto.creamList.trim().split("  ")
        for(cream in optionCreamList) {
            if(cream.length==0) {
                break
            }
            var info: List<String> = cream.split(" ");
            var detail: String = info.get(0)
            var price: Long = info.get(1).replace("원", "").toLong()
            val cakeOptionList: CakeOptionList = CakeOptionList(newShop, OptionTitleType.CFLAVOR, detail, price);
            cakeOptionListRepository.save(cakeOptionList)
        }

        var optionCreamColorList : List<String> = newShopReqDto.creamColorList.trim().split("  ")
        for(creamColor in optionCreamColorList) {
            if(creamColor.length==0) {
                break
            }
            var info: List<String> = creamColor.split(" ");
            var detail: String = info.get(0)
            var price: Long = info.get(1).replace("원", "").toLong()
            val cakeOptionList: CakeOptionList = CakeOptionList(newShop, OptionTitleType.CCOLOR, detail, price);
            cakeOptionListRepository.save(cakeOptionList)
        }

        var optionLetterList : List<String> = newShopReqDto.letterList.trim().split("  ")
        for(letter in optionLetterList) {
            if(letter.length==0) {
                break
            }
            var info: List<String> = letter.split(" ");
            var detail: String = info.get(0)
            var price: Long = info.get(1).replace("원", "").toLong()
            val cakeOptionList: CakeOptionList = CakeOptionList(newShop, OptionTitleType.LCOLOR, detail, price);
            cakeOptionListRepository.save(cakeOptionList)
        }

        println("저장완료~")
    }

    override fun getMyShop(seller: Seller): Shop? {
        val shop = shopRepository.findBySeller(seller)
        if (shop.isPresent) {
            return shop.get()
        }
        return null
    }

    override fun getByShopId(shopId: Long): Shop {
        return shopRepository.findByShopId(shopId)
    }
}