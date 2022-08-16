package com.example.cakezip.repository

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.shop.Shop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CakeOptionListRepository : JpaRepository<CakeOptionList, Long> {

    fun findByCakeOptionListId(id: Long) : Optional<CakeOptionList>

}