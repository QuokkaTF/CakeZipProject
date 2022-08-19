package com.example.cakezip.service

import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.NewShopReqDto
import com.example.cakezip.repository.ShopRepository

interface ShopService {
    fun addNewShop(newShopReqDto: NewShopReqDto)
    fun findBySeller(seller:Seller) : Shop

    fun searchShop(keyword:String):List<Shop>
}