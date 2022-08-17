package com.example.cakezip.service

import com.example.cakezip.dto.NewShopReqDto

interface ShopService {
    fun addNewShop(newShopReqDto: NewShopReqDto)

    fun showAllShop()
}