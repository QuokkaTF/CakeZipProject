package com.example.cakezip.repository

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface OrderRepository : JpaRepository<Orders, Long> {

}