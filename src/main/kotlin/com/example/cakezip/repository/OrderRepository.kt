package com.example.cakezip.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import com.example.cakezip.domain.shop.Shop
import java.util.*
import javax.persistence.EntityManager

interface OrderRepository : JpaRepository<OrderList, Long> {

}