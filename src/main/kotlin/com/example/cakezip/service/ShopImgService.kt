package com.example.cakezip.service

import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.domain.shop.ShopImg

interface ShopImgService {
    fun getShopImgs(shop: Shop) : List<ShopImg>
}