package com.example.cakezip.service

import com.example.cakezip.domain.OrderList
import com.example.cakezip.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
) {
//    fun findAll(): List<OrderList> = orderRepository.findAll()

    fun findAll(): List<OrderList>? {
        return orderRepository.findAll()
    }

//    fun findAllById(orderListId : Long): List<OrderList>? {
//        return orderRepository.findAllById()
//    }
    fun getOrderListById(orderListId: Long): OrderList? {
        return orderRepository.findAllByOrderListId(orderListId)
    }
}
