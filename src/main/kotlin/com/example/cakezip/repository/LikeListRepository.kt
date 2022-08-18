package com.example.cakezip.repository

import com.example.cakezip.domain.LikeList
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.LikeDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional


@Repository
interface LikeListRepository : JpaRepository<LikeList, Long> {
    // 좋아요가 DB에 있는지 확인
    fun findByCustomerAndShop(customer: Customer, shop: Shop): LikeList?

    fun countByShop(shop: Shop): Int?

    @Transactional
    fun deleteByCustomerAndShop(customer: Customer, shop: Shop)

    fun findByShop(shop: Shop): List<LikeList>?

    fun findByCustomer(customer: Customer): List<LikeList>
}