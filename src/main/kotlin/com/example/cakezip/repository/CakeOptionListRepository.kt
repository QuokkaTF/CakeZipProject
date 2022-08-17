package com.example.cakezip.repository

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.CakeTask
import com.example.cakezip.domain.cake.OptionTitleType
import com.example.cakezip.domain.shop.Shop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CakeOptionListRepository : JpaRepository<CakeOptionList, Long> {
    fun findByShopId (shop: Shop) : List<CakeOptionList>

    fun findByShopIdAndOptionTitle(shop:Shop, title:OptionTitleType) : List<CakeOptionList>
    fun deleteByCakeOptionListId(optionId: Long)

    fun findByCakeOptionListId(optionId:Long) : Optional<CakeOptionList>
}