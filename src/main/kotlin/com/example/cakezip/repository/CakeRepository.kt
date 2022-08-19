package com.example.cakezip.repository

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CakeRepository : JpaRepository<Cake, Long> {

    fun findByCustomerAndCakeStatus(customer: Customer, cakeStatus:String) : List<Cake>
    fun findByShopAndCakeStatusNot(shop: Shop, cakeStatus:String) : List<Cake>

    fun deleteAllByCakeId(id:Long)

    fun deleteAllByCustomerAndCakeStatus(customer:Customer, cakeStatus:String)

    fun findByCakeId(id:Long):Cake
}