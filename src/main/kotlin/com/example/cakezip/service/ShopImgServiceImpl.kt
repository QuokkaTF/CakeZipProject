package com.example.cakezip.service

import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.domain.shop.ShopImg
import com.example.cakezip.repository.ShopImgRepository
import org.springframework.stereotype.Service

@Service
class ShopImgServiceImpl(private val shopImgRepository: ShopImgRepository) : ShopImgService {
    override fun getShopImgs(shop: Shop): List<String> {
        val imgList = shopImgRepository.findByShop(shop)
        val retrunList : ArrayList<String> = ArrayList()
        for (img in imgList) {
            retrunList.add(img.shopImgUrl)
        }
        return retrunList
    }
}