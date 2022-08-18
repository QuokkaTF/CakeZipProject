package com.example.cakezip.repository

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.Review
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.notice.Notice
import com.example.cakezip.domain.shop.Shop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional


@Repository
interface NoticeRepository : JpaRepository<Notice, Long> {

    fun findByCustomer(customer: Customer): List<Notice>

    fun findBySeller(seller: Seller): List<Notice>

    @Transactional
    fun deleteByCustomer(customer: Customer)

    @Transactional
    fun deleteBySeller(seller: Seller)

    @Transactional
    fun deleteByNoticeId(noticeId: Long)

}