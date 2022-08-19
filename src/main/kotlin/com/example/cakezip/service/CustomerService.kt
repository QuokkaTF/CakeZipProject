package com.example.cakezip.service

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.repository.CakeRepository
import com.example.cakezip.repository.CustomerRepository
import org.springframework.stereotype.Service


@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val cakeRepository: CakeRepository,
    private val orderService: OrderService,
) {
    //fun findCustomerByCustomerId(id: Long) : Customer = customerRepository.findByCustomerId(id)

    //fun findAllCakeByCustomerId(id: Long) : List<Cake> = cakeRepository.findByCustomerId(id)

    fun findByCustomerId(id: Long): Customer = customerRepository.findByCustomerId(id)



}