package com.example.cakezip.repository

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.User
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long>{
    fun findByCustomerId(customerId:Long): Customer

    fun findCustomerByUser(user: User): Customer
}