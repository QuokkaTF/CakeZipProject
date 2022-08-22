package com.example.cakezip.service

import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.domain.shop.ShopImg
import com.example.cakezip.repository.ShopImgRepository
import org.springframework.stereotype.Service

@Service
class ShopImgServiceImpl(private val shopImgRepository: ShopImgRepository) : ShopImgService {
    override fun getShopImgs(shop: Shop): List<ShopImg> {
        return shopImgRepository.findByShop(shop)
    }

    override fun deleteImage(imageId: Long): Long {
        val shopImg:ShopImg = shopImgRepository.findByShopImgId(imageId)
        val shopId = shopImg.shop.shopId;
        shopImgRepository.deleteByShopImgId(imageId)
        if (shopId == null) {
            throw Exception("잘못된 요청입니다.")
        }
        return shopId
    }

    override fun getThumbnail(shop: Shop): ShopImg {
        return shopImgRepository.findByShop(shop)[0]
    }

}
