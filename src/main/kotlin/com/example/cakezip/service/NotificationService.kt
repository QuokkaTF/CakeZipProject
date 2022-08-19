package com.example.cakezip.service

import com.example.cakezip.domain.Orders
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
    private val orderRepository: OrderRepository,
    private val notificationRepository: NotificationRepository,
    private val shopRepository: ShopRepository,
) {

    fun makeNotice(customerId: Long, sellerId: Long, order: Orders?, notificationMessage: NotificationMessage, notificationType: NotificationType) {
        val customer = customerRepository.findByCustomerId(customerId)
        val seller = sellerRepository.findBySellerId(sellerId)
        val noticeList : ArrayList<NotificationDto> = ArrayList()

        if (notificationMessage.equals(NotificationType.TOSELLER)) {
            val newNotification = Notification(
                customer = customer,
                seller = seller,
                order = order,
                notificationMessage = notificationMessage,
                notificationType = notificationType,
            )
            notificationRepository.save(newNotification)
            //noticeList.add(NewNoticeDto())
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

    fun getCustomerNotices(customerId: Long): List<NotificationDto>? {
        val customer = customerRepository.findByCustomerId(customerId)
        val noticeList: ArrayList<NotificationDto> = ArrayList()

        for (notice in notificationRepository.findByCustomer(customer)) {
            //val order = orderRepository.findOrdersByCake()
            noticeList.add(NotificationDto(notice.noticeId, notice.order, notice.notificationMessage.toString(), notice.notificationType.toString()))
        }
        println(noticeList)
        return noticeList
    }

    fun getSellerNotices(sellerId: Long): List<Notification>? {
        val seller = customerRepository.findByCustomerId(sellerId)
        return notificationRepository.findByCustomer(seller)
    }


    fun deleteNotice(noticeId: Long) = notificationRepository.deleteByNoticeId(noticeId)


}