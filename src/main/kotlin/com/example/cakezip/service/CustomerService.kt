package com.example.cakezip.service

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.repository.CakeRepository
import com.example.cakezip.repository.CustomerRepository
import com.example.cakezip.repository.OrderDetailRepository
import org.springframework.stereotype.Service


@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val cakeRepository: CakeRepository,
    private val orderService: OrderService,
    private val orderDetailRepository: OrderDetailRepository,
) {
    //fun findCustomerByCustomerId(id: Long) : Customer = customerRepository.findByCustomerId(id)

    //fun findAllCakeByCustomerId(id: Long) : List<Cake> = cakeRepository.findByCustomerId(id)

    fun findByCustomerId(id: Long): Customer = customerRepository.findByCustomerId(id)

}