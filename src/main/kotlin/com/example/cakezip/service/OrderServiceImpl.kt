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

        for (cake in cakeRepository.findByCustomerAndCakeStatusNot(customer, CakeStatusType.CART)) {
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
//                throw Exception("이미 취소된 주문입니다.")
                return -1
            }
            CakeStatusType.REJECT -> {
                return -2
//                throw Exception("이미 거절된 주문입니다.")
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
//                throw Exception("이미 진행중인 주문은 취소할 수 없습니다.")
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
}
