package com.example.cakezip.service

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.notice.Notification
import com.example.cakezip.domain.notice.NotificationMessage
import com.example.cakezip.domain.notice.NotificationType
import com.example.cakezip.dto.NotificationDto
import com.example.cakezip.repository.*
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val customerRepository: CustomerRepository,
    private val sellerRepository: SellerRepository,
    private val notificationRepository: NotificationRepository,
) {
    fun makeNotification(
        customerId: Long,
        sellerId: Long,
        order: Orders,
        notificationMessage: NotificationMessage,
        notificationType: NotificationType
    ) {
        val customer = customerRepository.findByCustomerId(customerId)
        val seller = sellerRepository.findBySellerId(sellerId)

        if (notificationMessage.equals(NotificationType.TOSELLER)) {
            val newNotification = Notification(
                customer = customer,
                seller = seller,
                order = order,
                notificationMessage = notificationMessage,
                notificationType = notificationType,
            )
            notificationRepository.save(newNotification)
        } else {
            val newNotification = Notification(
                customer = customer,
                seller = seller,
                order = order,
                notificationMessage = notificationMessage,
                notificationType = NotificationType.TOCUSTOMER,
            )
            notificationRepository.save(newNotification)
        }
    }

    fun getCNotifications(customer: Customer): List<NotificationDto>? {
        val noticeList: ArrayList<NotificationDto> = ArrayList()

        for (notice in notificationRepository.findByCustomer(customer)) {
            noticeList.add(
                NotificationDto(
                    notice.noticeId,
                    notice.order,
                    notice.notificationMessage.toString(),
                    notice.notificationType.toString()
                )
            )
        }
        return noticeList
    }

    fun getSNotifications(seller: Seller): List<NotificationDto>? {
        val noticeList: ArrayList<NotificationDto> = ArrayList()

        for (notice in notificationRepository.findBySeller(seller)) {
            noticeList.add(
                NotificationDto(
                    notice.noticeId,
                    notice.order,
                    notice.notificationMessage.toString(),
                    notice.notificationType.toString()
                )
            )
        }
        return noticeList
    }

    fun deleteNotification(noticeId: Long) = notificationRepository.deleteByNoticeId(noticeId)
}

