package com.example.cakezip.service

import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.notice.NotificationMessage
import com.example.cakezip.domain.notice.NotificationType
import com.example.cakezip.dto.OrderDto
import com.example.cakezip.repository.*
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val cakeRepository: CakeRepository,
    private val customerRepository: CustomerRepository,
    private val noticeService: NotificationService,
) : OrderService {

    // 오더 아이디로 케이크 아이디 찾기 -> 케이크 아이디로 띄워주기
    //// customerId 받아와서 모든 오더 + 케이크 띄우기
    //
    override fun getCustomerAllOrders(CustomerId: Long): List<OrderDto>? {
        val customer = customerRepository.findByCustomerId(CustomerId)
        val cakeList: ArrayList<OrderDto> = ArrayList()

        for (cake in cakeRepository.findByCustomerAndCakeStatusNot(customer, CakeStatusType.CART)) {
            println("1")
            val order = orderRepository.findOrdersByCake(cake)
            println("2")
            cakeList.add(OrderDto(order?.orderId, cake.customer.user.userName, cake.shop.shopName, cake))
            println("3")
        }
        println(cakeList)
        println("4")
        println("된다고?")

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
        //val statusCancel = CakeStatusType.CANCEL

        if (cake.cakeStatus.equals(CakeStatusType.CANCEL)) {
            //TODO: 이미 주문 취소일 경우 예외 처리
            //TODO: 상태가 준비중 전일 때만 주문 취소 가능하게 처리
            println("이미 CANCEL")
        } else {
            println("CANCEL 완")
            cake.cakeStatus = CakeStatusType.CANCEL
            cakeRepository.save(cake)
            noticeService.makeNotice(
                cake.customer.customerId, cake.shop.seller.sellerId, order,
                NotificationMessage.ORDER_CANCEL, NotificationType.TOSELLER
            )
            println("알림 생성")

        }
    }


}
