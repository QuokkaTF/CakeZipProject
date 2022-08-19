package com.example.cakezip.service

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.repository.CakeOptionListRepository
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class CakeOptionListService(
    private val cakeOptionListRepository: CakeOptionListRepository,
    ) {

    fun findByCakeOptionListId(id: Long) : Optional<CakeOptionList> = cakeOptionListRepository.findByCakeOptionListId(id)

    fun findByOptionTitleAndOptionDetail(title:String, detail:String):CakeOptionList =
        cakeOptionListRepository.findByOptionTitleAndOptionDetail(title, detail)
}