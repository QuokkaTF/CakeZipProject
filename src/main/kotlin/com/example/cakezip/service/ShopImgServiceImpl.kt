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
}