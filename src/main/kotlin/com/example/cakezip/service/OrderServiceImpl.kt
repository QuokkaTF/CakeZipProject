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
    private val noticeService: NotificationService,
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


    override fun changeCakeStateCancel(CakeId: Long) {
        val cake = cakeRepository.findByCakeId(CakeId)
        val order = orderRepository.findOrdersByCake(cake)

        if (cake.cakeStatus.equals(CakeStatusType.CANCEL)) {
            //TODO: 이미 주문 취소일 경우 예외 처리
            //TODO: 상태가 준비중 전일 때만 주문 취소 가능하게 처리

            println("이미 CANCEL")
        } else {
            println("CANCEL 완")
            cake.cakeStatus = CakeStatusType.CANCEL
            cakeRepository.save(cake)
            noticeService.makeNotification(
                cake.customer.customerId, cake.shop.seller!!.sellerId!!, order,
                NotificationMessage.ORDER_CANCEL, NotificationType.TOSELLER
            )
            println("알림 생성")

        }
    }

    override fun addOrder(merchantUid: String, merchantPrice: Long, customer: Customer,cake: Cake): Orders {
        val order = Orders(
            merchantUid = merchantUid,
            merchantPrice = merchantPrice,
            customer = customer,
            cake =cake,
        )
        Orders()
        return orderRepository.save(order)
    }

}

