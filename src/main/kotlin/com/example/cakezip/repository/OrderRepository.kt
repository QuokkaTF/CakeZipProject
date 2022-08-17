package com.example.cakezip.repository

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.member.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface OrderRepository : JpaRepository<Orders, Long> {
    //fun findAllBy(): List<OrderList>?
    //fun findAllByOrderListId(orderListId: Long): OrderList?
    //fun findAllByCustomerId(customerId: Long): Order?

//    @Query("SELECT o from Orders o" +
//           " WHERE o.customer.customerId=:id")
//    fun findOrdersByCustomerId(id: Long) : List<Orders>?

//    @Query("SELECT com.example.cakezip.dto.OrderListDto(o.orderId, o.customer.user.userName, o.merchantPrice d.cake.shop.shopName) FROM Orders o, OrderDetail d"
//            + " WHERE o.orderId = d.order.orderId AND o.customer.customerId=:id", nativeQuery = true)
//    fun findOrdersByCustomerId(id: Long) : List<OrderListDto>?

    fun findOrdersByCustomer(customer: Customer) : List<Orders>


    




}