package com.example.cakezip.service

import com.example.cakezip.domain.OrderDetail
import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.OrderDto

interface OrderService {
    //fun findCakesByOrderId(id: Long): List<Cake>
    //fun findOrdersByCustomerId(id: Long): List<Orders>
    //fun findOrderDetailByOrderId(id: Long): OrderDetail
    //fun findOrderDetailByCustomerId(id: Long): List<OrderDetail>


    fun getCustomerAllOrders(CustomerId: Long) : List<OrderDto>?

    fun getCustomerOrderDetail(CakeId: Long) : OrderDto

    fun changeCakeStateCancel(id: Long)


}

