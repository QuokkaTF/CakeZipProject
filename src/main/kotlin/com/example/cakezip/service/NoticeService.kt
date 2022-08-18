package com.example.cakezip.service

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.notice.Notice
import com.example.cakezip.domain.notice.NoticeMessage
import com.example.cakezip.domain.notice.NoticeType
import com.example.cakezip.dto.NoticeDto
import com.example.cakezip.repository.*
import org.springframework.stereotype.Service


@Service
class NoticeService(
    private val customerRepository: CustomerRepository,
    private val sellerRepository: SellerRepository,
    private val orderRepository: OrderRepository,
    private val noticeRepository: NoticeRepository,
    private val shopRepository: ShopRepository,
) {

    fun makeNotice(customerId: Long, sellerId: Long, order: Orders?, noticeMessage: NoticeMessage, noticeType: NoticeType) {
        val customer = customerRepository.findByCustomerId(customerId)
        val seller = sellerRepository.findBySellerId(sellerId)
        val noticeList : ArrayList<NoticeDto> = ArrayList()

        if (noticeMessage.equals(NoticeType.TOSELLER)) {
            val newNotice = Notice(
                customer = customer,
                seller = seller,
                order = order,
                noticeMessage = noticeMessage,
                noticeType = noticeType,
            )
            noticeRepository.save(newNotice)
            //noticeList.add(NewNoticeDto())
        } else {
            val newNotice = Notice(
                customer = customer,
                seller = seller,
                order = order,
                noticeMessage = noticeMessage,
                noticeType = NoticeType.TOCUSTOMER,
            )
            noticeRepository.save(newNotice)
        }
    }

    fun getCustomerNotices(customerId: Long): List<NoticeDto>? {
        val customer = customerRepository.findByCustomerId(customerId)
        val noticeList: ArrayList<NoticeDto> = ArrayList()

        for (notice in noticeRepository.findByCustomer(customer)) {
            //val order = orderRepository.findOrdersByCake()
            noticeList.add(NoticeDto(notice.noticeId, notice.order, notice.noticeMessage.toString(), notice.noticeType.toString()))
        }
        println(noticeList)
        return noticeList
    }

    fun getSellerNotices(sellerId: Long): List<Notice>? {
        val seller = customerRepository.findByCustomerId(sellerId)
        return noticeRepository.findByCustomer(seller)
    }


    fun deleteNotice(noticeId: Long) {
        noticeRepository.deleteByNoticeId(noticeId)
    }

}