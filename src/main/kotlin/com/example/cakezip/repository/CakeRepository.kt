package com.example.cakezip.repository

import com.example.cakezip.cake.Cake
import com.example.cakezip.domain.shop.Shop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CakeRepository : JpaRepository<Cake, Long> {

    @Query("select pickupDate from Cake c where c.cakeId=:id")
    fun findPickupDate(id:Long): String?

    @Query("select shop from Cake c where c.cakeId=:id")
    fun findShop(id:Long): Shop

    @Query("select cakeId from Cake")
    fun findId(): List<Long>

//    @Query ("select c, ct from Cake c left join ")
}