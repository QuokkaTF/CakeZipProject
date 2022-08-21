package com.example.cakezip.service

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.NewShopReqDto
import com.example.cakezip.dto.ShopDetailInfoDto
import com.example.cakezip.dto.ShopSimpleInfoDto

interface ShopService {
    fun getMyShop(seller: Seller) : Shop?

    fun getByShopId(shopId:Long) : Shop

    fun getAllShopSimpleList() : List<ShopSimpleInfoDto>

    fun getShopDetail(customer: Customer?, shopID:Long) : ShopDetailInfoDto
    
    fun updateShopInfo(shopID: Long, shop:Shop) : Shop

    fun deleteShop(shopId: Long)

    fun addNewShop(newShopReqDto: NewShopReqDto)
    
    fun findBySeller(seller:Seller) : Shop

    fun searchShop(keyword:String):List<Shop>
}
