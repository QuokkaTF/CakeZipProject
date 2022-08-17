package com.example.cakezip.repository

import com.example.cakezip.domain.member.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long> {

    fun findByCustomerId(id: Long): Customer
}