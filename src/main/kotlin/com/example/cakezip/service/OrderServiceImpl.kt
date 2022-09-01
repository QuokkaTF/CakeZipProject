package com.example.cakezip.service

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.notice.NotificationMessage
import com.example.cakezip.domain.notice.NotificationType
import com.example.cakezip.dto.OrderDto
import com.example.cakezip.repository.*
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val cakeRepository: CakeRepository,
    private val notificationService: NotificationService,
) : OrderService {
    override fun getCustomerAllOrders(customer: Customer): List<OrderDto>? {
        val cakeList: ArrayList<OrderDto> = ArrayList()

        for (cake in cakeRepository.findByCustomerAndCakeStatusNotOrderByCreatedAtDesc(customer, CakeStatusType.CART)) {
            val order = orderRepository.findOrdersByCake(cake)
            cakeList.add(OrderDto(order?.orderId, cake.customer.user.userName, cake.shop.shopName, cake))
        }
        return cakeList
    }

    override fun getCustomerOrders(CakeId: Long): OrderDto {
        val cake = cakeRepository.findByCakeId(CakeId)
        val order = orderRepository.findOrdersByCake(cake)

        return OrderDto(order?.orderId, cake.customer.user.userName, cake.shop.shopName, cake)
    }

    override fun changeCakeStateCancel(CakeId: Long): Int {
        val cake = cakeRepository.findByCakeId(CakeId)
        val order = orderRepository.findOrdersByCake(cake)

        when (cake.cakeStatus) {
            CakeStatusType.CANCEL -> {
                return -1
            }
            CakeStatusType.REJECT -> {
                return -2
            }
            CakeStatusType.PAYMENT -> {
                cake.cakeStatus = CakeStatusType.CANCEL
                cakeRepository.save(cake)
                notificationService.makeNotification(
                    cake.customer.customerId, cake.shop.seller!!.sellerId!!, order!!,
                    NotificationMessage.ORDER_CANCEL, NotificationType.TOSELLER
                )
                return 0
            }
            else -> {
                return -3
            }
        }
    }

    override fun addOrder(merchantUid: String, merchantPrice: Long, customer: Customer, cake: Cake): Orders {
        val order = Orders(
            merchantUid = merchantUid,
            merchantPrice = merchantPrice,
            customer = customer,
            cake = cake,
        )
        Orders()
        return orderRepository.save(order)
    }

    override fun findByCake(cake: Cake): Orders? = orderRepository.findOrdersByCake(cake)

    override fun cakeStateNoti(CakeId: Long) {
        val cake = cakeRepository.findByCakeId(CakeId)

        var notiMessage: NotificationMessage? = null
        when (cake.cakeStatus) {
            CakeStatusType.REJECT -> {
                notiMessage = NotificationMessage.ORDER_DENIED
            }
            CakeStatusType.PROCEED -> {
                notiMessage = NotificationMessage.ORDER_ACCEPTED
            }
            CakeStatusType.READY -> {
                notiMessage = NotificationMessage.ORDER_READY
            }
            else->
                return
        }

        notificationService.makeNotification(
            cake.customer.customerId, cake.shop.seller!!.sellerId!!, findByCake(cake)!!,
            notiMessage!!, NotificationType.TOCUSTOMER
        )
    }
}

