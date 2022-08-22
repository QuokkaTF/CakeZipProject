package com.example.cakezip.repository

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.cake.Cake
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Orders, Long> {
    fun findOrdersByCake(cake: Cake): Orders?
}
