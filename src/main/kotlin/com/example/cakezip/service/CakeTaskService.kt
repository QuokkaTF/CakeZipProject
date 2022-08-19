package com.example.cakezip.service

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.CakeTask
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeTask
import com.example.cakezip.repository.CakeRepository
import com.example.cakezip.repository.CakeTaskRepository
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional


@Service
class CakeTaskService(
    private val cakeTaskRepository: CakeTaskRepository,
    private val cakeRepository: CakeRepository
    ) {

    fun findByCake(cake:Cake) : List<CakeTask> = cakeTaskRepository.findByCake(cake)

    @Transactional
    fun deleteAllByCake_cakeId(id:Long) = cakeTaskRepository.deleteAllByCake_cakeId(id)

    @Transactional
    fun deleteAllByCake(cake:Cake) = cakeTaskRepository.deleteAllByCake(cake)

    fun addCartCakeTask(
        cake: Cake,cakeOptionList: CakeOptionList
    ): CakeTask {
        val caketask = CakeTask(
            cake = cake,
            cakeOptionList = cakeOptionList,
        )
        Orders()
        return cakeTaskRepository.save(caketask)
    }
}