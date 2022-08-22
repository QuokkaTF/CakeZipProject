package com.example.cakezip.service

import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.domain.shop.ShopImg

interface ShopImgService {
    fun getShopImgs(shop: Shop) : List<ShopImg>
    fun getThumbnail(shop:Shop):ShopImg
    fun deleteImage(imageId: Long) : Long
}