package com.example.cakezip.service

import com.example.cakezip.dto.OrderDto
import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.member.Customer
import org.springframework.stereotype.Service

@Service
interface OrderService {
    fun getCustomerAllOrders(customer: Customer): List<OrderDto>?

    fun getCustomerOrders(cakeId: Long): OrderDto

    fun changeCakeStateCancel(id: Long): Int

    fun addOrder(merchantUid: String, merchantPrice: Long, customer: Customer, cake: Cake): Orders
}

