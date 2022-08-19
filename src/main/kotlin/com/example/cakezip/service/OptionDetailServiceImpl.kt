package com.example.cakezip.service

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.OptionTitleType
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.repository.CakeOptionListRepository
import com.example.cakezip.repository.ShopRepository
import org.springframework.stereotype.Service

@Service
class OptionDetailServiceImpl(private val shopRepository: ShopRepository, private val cakeOptionListRepository: CakeOptionListRepository) : OptionDetailService {

    override fun getOptionDetailByShopAndType(shopId: Long, type: OptionTitleType)  : List<CakeOptionList>{
        var shop:Shop = shopRepository.findByShopId(shopId)
        var detailList : List<CakeOptionList> = cakeOptionListRepository.findByShopIdAndOptionTitle(shop, type)
        return detailList
    }

}