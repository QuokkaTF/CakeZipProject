package com.example.cakezip.service

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
) {
    fun findByCustomerId(id: Long): Customer = customerRepository.findByCustomerId(id)
}