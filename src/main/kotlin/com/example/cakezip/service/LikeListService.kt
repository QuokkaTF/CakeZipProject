package com.example.cakezip.service

import com.example.cakezip.domain.LikeList
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.domain.shop.ShopImg
import com.example.cakezip.dto.ShopSimpleInfoDto
import com.example.cakezip.repository.*
import org.springframework.stereotype.Service

@Service
class LikeListService(
    private val likeListRepository: LikeListRepository,
    private val shopImgRepository: ShopImgRepository,
) {

    fun isLike(customer: Customer, shop: Shop): Boolean {
        return likeListRepository.findByCustomerAndShop(customer, shop) != null // 있으면 True 없으면 False
    }

    fun addLike(customer: Customer, shop: Shop): Boolean {
        if (isLike(customer, shop)) {
            likeListRepository.deleteByCustomerAndShop(customer, shop)
            return false
        }
        val newLike = LikeList(
            shop = shop,
            customer = customer,
        )
        likeListRepository.save(newLike)

        return true
    }

    fun getShopLikeCount(shop: Shop): Int = likeListRepository.countByShop(shop)

    fun getCustomerLikeCount(customer: Customer): Int = likeListRepository.countByCustomer(customer)

    fun getLikedShopList(customer: Customer): List<ShopSimpleInfoDto> {
        val shopSimpleInfoList: ArrayList<ShopSimpleInfoDto> = ArrayList()

        for (liked in likeListRepository.findByCustomer(customer)) {
            val shopImgList: ArrayList<String> = ArrayList()

            val getShopImg: List<ShopImg> = shopImgRepository.findByShop(liked.shop)
            for (img in getShopImg) {
                shopImgList.add(img.shopImgUrl)
            }

            val shopSimpleDto = ShopSimpleInfoDto(
                liked.shop.shopId,
                liked.shop.shopName,
                liked.shop.shopShortDescriptor,
                liked.shop.shopArea,
                shopImgList
            )
            shopSimpleInfoList.add(shopSimpleDto)
        }
        return shopSimpleInfoList
    }
}



