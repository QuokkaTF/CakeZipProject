package com.example.cakezip.repository

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.notice.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional


@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findByCustomer(customer: Customer): List<Notification>

    @Transactional
    fun deleteByNoticeId(noticeId: Long)

}