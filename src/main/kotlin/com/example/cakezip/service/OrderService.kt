package com.example.cakezip.service

import com.example.cakezip.domain.OrderList
import com.example.cakezip.repository.OrderRepository
import org.springframework.stereotype.Service

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.Review
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.repository.CakeRepository
import com.example.cakezip.repository.CustomerRepository
import com.example.cakezip.repository.OrderRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

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
    
     fun addOrder(merchantUid: String, merchantPrice: Long, customer: Customer,cake: Cake): Orders {
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

