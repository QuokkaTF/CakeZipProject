package com.example.cakezip.service

import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.ShopAddressDto
import com.example.cakezip.repository.ShopRepository
import org.springframework.stereotype.Service

@Service
class MapService(private val shopRepository: ShopRepository) {

    fun findAllShop(): MutableList<ShopAddressDto> {
        val shops: List<Shop> = shopRepository.findAllByStatus("active")
        var shopDtoList: MutableList<ShopAddressDto> = mutableListOf<ShopAddressDto>()
        println(shops.size)
        for(shop in shops) {
            shopDtoList.add(ShopAddressDto(shop.shopId,shop.shopAddressMain,shop.shopName))
        }

        return shopDtoList
    }


}