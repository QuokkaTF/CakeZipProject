package com.example.cakezip.service

import com.example.cakezip.dto.OrderDto

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
interface OrderService {
    fun getCustomerAllOrders(customerId: Long) : List<OrderDto>?

    fun getCustomerOrders(cakeId: Long) : OrderDto

    fun changeCakeStateCancel(id: Long)

    fun getOrderListById(orderListId: Long): OrderList?
    
    fun addOrder(merchantUid: String, merchantPrice: Long, customer: Customer,cake: Cake): Orders
    
}
