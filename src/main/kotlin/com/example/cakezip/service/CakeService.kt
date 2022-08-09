package com.example.cakezip.service

import com.example.cakezip.cake.Cake
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.repository.CakeRepository
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Service


@Service
class CakeService(
    private val cakeRepository: CakeRepository,
    ) {

    fun findAll(): List<Cake> = cakeRepository.findAll()

    fun findId(): List<Long> = cakeRepository.findId()

    fun findPickupDate(id:Long): String? = cakeRepository.findPickupDate(id)

    fun findShop(id:Long): Shop = cakeRepository.findShop(id)




}