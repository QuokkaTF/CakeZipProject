package com.example.cakezip.service

import com.example.cakezip.dto.OrderDto

interface OrderService {
    fun getCustomerAllOrders(customerId: Long) : List<OrderDto>?

    fun getCustomerOrders(cakeId: Long) : OrderDto

    fun changeCakeStateCancel(id: Long)

}

