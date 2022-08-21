package com.example.cakezip.repository

import com.example.cakezip.domain.LikeList
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional


@Repository
interface LikeListRepository : JpaRepository<LikeList, Long> {
    fun findByCustomerAndShop(customer: Customer, shop: Shop): LikeList?

    fun countByShop(shop: Shop): Int

    @Transactional
    fun deleteByCustomerAndShop(customer: Customer, shop: Shop)

    fun findByCustomer(customer: Customer): List<LikeList>

    fun countByCustomer(customer: Customer): Int
}

