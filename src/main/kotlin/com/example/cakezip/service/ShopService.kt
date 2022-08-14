package com.example.cakezip.service

import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.NewShopReqDto
import com.example.cakezip.dto.ShopSimpleInfoDto

interface ShopService {
    fun addNewShop(newShopReqDto: NewShopReqDto)
    fun getMyShop(seller: Seller) : Shop?

    fun getByShopId(shopId:Long) : Shop

    fun getAllShopSimpleList() : List<ShopSimpleInfoDto>
}