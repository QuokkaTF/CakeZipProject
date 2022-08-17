package com.example.cakezip.service

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.CakeTask
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.repository.CakeOptionListRepository
import com.example.cakezip.repository.CakeRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class CakeOptionListService(
    private val cakeOptionListRepository: CakeOptionListRepository,
) {

    fun findByCakeOptionListId(id: Long) : Optional<CakeOptionList> = cakeOptionListRepository.findByCakeOptionListId(id)

}