package com.example.cakezip.service

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.OptionTitleType
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.domain.shop.ShopImg
import com.example.cakezip.dto.NewShopReqDto
import com.example.cakezip.dto.ShopDetailInfoDto
import com.example.cakezip.dto.ShopSimpleInfoDto
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

    override fun getAllShopSimpleList(): List<ShopSimpleInfoDto> {
        var shopSimpleInfoList : ArrayList<ShopSimpleInfoDto> = ArrayList()
        val shopList : List<Shop> = shopRepository.findAllByStatus("active")

        for (shop in shopList) {
            val shopImgList : ArrayList<String> = ArrayList()

            val getShopImg : List<ShopImg> = shopImgRepository.findByShop(shop)
            for (img in getShopImg) {
                shopImgList.add(img.shopImgUrl)
            }

            var shopSimpleDto = ShopSimpleInfoDto(shop.shopId, shop.shopName, shop.shopShortDescriptor, shop.shopArea, shopImgList)
            shopSimpleInfoList.add(shopSimpleDto)
        }
        println(shopSimpleInfoList.toString())
        return shopSimpleInfoList;
    }

    override fun getShopDetail(shopID: Long): ShopDetailInfoDto {
        val shop : Shop = shopRepository.findByShopId(shopID)
        val shopImg : List<ShopImg> = shopImgRepository.findByShop(shop)
        val shopImgUrl : ArrayList<String> = ArrayList()
        for (img in shopImg) {
            shopImgUrl.add(img.shopImgUrl)
        }

        var cakeOptionList : List<CakeOptionList> = cakeOptionListRepository.findByShopId(shop)
        val designOptionList : ArrayList<CakeOptionList> = ArrayList()
        val sizeOptionList : ArrayList<CakeOptionList> = ArrayList()
        val sheetOptionList : ArrayList<CakeOptionList> = ArrayList()
        val creamOptionList : ArrayList<CakeOptionList> = ArrayList()
        val creamColorOptionList : ArrayList<CakeOptionList> = ArrayList()
        val letterOptionList : ArrayList<CakeOptionList> = ArrayList()

        for (option in cakeOptionList) {
            when(option.optionTitle) {
                OptionTitleType.DESIGN -> designOptionList.add(option)
                OptionTitleType.SIZE -> sizeOptionList.add(option)
                OptionTitleType.SFLAVOR -> sheetOptionList.add(option)
                OptionTitleType.CFLAVOR -> creamOptionList.add(option)
                OptionTitleType.CCOLOR -> creamColorOptionList.add(option)
                OptionTitleType.LCOLOR -> letterOptionList.add(option)
            }
        }
        return ShopDetailInfoDto(shopID, shop.shopName, shop.shopAddress, shop.shopArea, shop.shopShortDescriptor, shop.shopPhoneNum, shop.seller, shop.shopImgDescriptionUrl, shopImgUrl, designOptionList, sizeOptionList, sheetOptionList, creamOptionList, creamColorOptionList, letterOptionList)
    }

    override fun updateShopInfo(shop: Shop): Shop {
        return shopRepository.save(shop)
    }
}