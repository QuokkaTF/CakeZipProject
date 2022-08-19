package com.example.cakezip.service

import com.example.cakezip.domain.Orders
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
    fun findByCakeId(id: Long): Cake = cakeRepository.findByCakeId(id)

    fun findByCustomerAndCakeStatus(customer: Customer, cakeStatus: String): List<Cake> =
        cakeRepository.findByCustomerAndCakeStatus(customer, cakeStatus)

    fun findByShopAndCakeStatusNot(shop: Shop, cakeStatus: String): List<Cake> =
        cakeRepository.findByShopAndCakeStatusNot(shop, cakeStatus)

    @Transactional
    fun deleteAllByCakeId(id: Long) = cakeRepository.deleteAllByCakeId(id)

    @Transactional
    fun deleteAllByCustomerAndCakeStatus(customer: Customer, cakeStatus: String) =
        cakeRepository.deleteAllByCustomerAndCakeStatus(customer, cakeStatus)


    @Transactional
    fun updateCakeStatus(CakeId: Long, statusCheck: String) {
        val cake = cakeRepository.findByCakeId(CakeId)

        if (cake.cakeStatus.equals(statusCheck)) {
            //TODO: 이미 주문 취소일 경우 예외 처리
        } else {
            cake.cakeStatus = statusCheck
            cakeRepository.save(cake)
        }
    }

    fun addCartCake(
        pickupDate: String, letterText: String, etc: String, totalPrice: Int, cakeStatus: String,
        shop: Shop, customer: Customer
    ): Cake {
        val cake = Cake(
            pickupDate = pickupDate,
            letterText = letterText,
            etc = etc,
            totalPrice = totalPrice,
            cakeStatus = cakeStatus,
            shop = shop,
            customer = customer,
        )
        return cakeRepository.save(cake)
    }
}