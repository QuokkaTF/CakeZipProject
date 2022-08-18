package com.example.cakezip.repository

import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.domain.shop.ShopImg
import org.springframework.data.jpa.repository.JpaRepository

interface ShopImgRepository : JpaRepository<ShopImg, Long> {
    fun findByShop(shop:Shop) : List<ShopImg>
    fun findByShopImgId(shopImg: Long) : ShopImg

    fun deleteByShopImgId(shopImgId:Long)
}