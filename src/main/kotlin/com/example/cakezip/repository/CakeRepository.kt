package com.example.cakezip.repository

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import org.springframework.data.jpa.repository.JpaRepository

interface CakeRepository : JpaRepository<Cake, Long> {

    fun findByCustomerOrderByCreatedAtDesc(customer: Customer) : List<Cake>
    fun findByShop(shop: Shop) : List<Cake>
    fun findByCustomerAndCakeStatusOrderByCreatedAtDesc(customer: Customer, cakeStatus:CakeStatusType) : List<Cake>
    fun findByCustomerAndCakeStatusNotOrderByCreatedAtDesc(customer: Customer, cakeStatus:CakeStatusType) : List<Cake>
    fun findByShopAndCakeStatusNot(shop: Shop, cakeStatus:CakeStatusType) : List<Cake>

    fun deleteAllByCakeId(id:Long)

    fun deleteAllByCustomerAndCakeStatus(customer:Customer, cakeStatus:CakeStatusType)

    fun findByCakeId(id:Long):Cake

    fun countByCustomerAndCakeStatus(customer:Customer, cakeStatus:CakeStatusType):Int
}