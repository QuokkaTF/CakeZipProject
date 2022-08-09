package com.example.cakezip.repository

import com.example.cakezip.domain.OrderList
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.EntityManager




interface OrderRepository : JpaRepository<OrderList, Long> {
    //fun findAllBy(): List<OrderList>?
    fun findAllByOrderListId(orderListId: Long): OrderList?
    //fun deleteAllById(orderId: Long)
}