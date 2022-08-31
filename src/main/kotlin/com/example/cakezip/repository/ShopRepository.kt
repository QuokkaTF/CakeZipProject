package com.example.cakezip.repository

import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.shop.Shop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ShopRepository : JpaRepository<Shop, Long> {

    fun findBySeller (seller : Seller) : Optional<Shop>

    fun findByShopId (shopId : Long) : Shop

    fun findByShopNameContaining(keyword:String) : List<Shop>

    fun findAllByStatusOrderByCreatedAtDesc(status:String) : List<Shop>
}