package com.example.cakezip.service

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.repository.CakeRepository
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CakeService(
    private val cakeRepository: CakeRepository,
) {

    fun findByCakeId(id:Long):Cake = cakeRepository.findByCakeId(id)

    fun findByCustomerAndCakeStatus(customer:Customer, cakeStatus:String): List<Cake> = cakeRepository.findByCustomerAndCakeStatus(customer, cakeStatus)

    @Transactional
    fun deleteAllByCakeId(id:Long) = cakeRepository.deleteAllByCakeId(id)


    @Transactional
    fun deleteAllByCustomerAndCakeStatus(customer:Customer, cakeStatus:String) = cakeRepository.deleteAllByCustomerAndCakeStatus(customer,cakeStatus)


    fun findByCustomerAndCakeStatusNot(customer:Customer, cakeStatus:CakeStatusType): List<Cake> =
        cakeRepository.findByCustomerAndCakeStatusNot(customer, cakeStatus)


}