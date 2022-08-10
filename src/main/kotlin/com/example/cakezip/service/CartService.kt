package com.example.cakezip.service

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.repository.CartRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CartService(
    private val cartRepository: CartRepository,
    ) {

//    fun findAll(): List<Cake> = cartRepository.findAll()

    fun findAll(): List<Cake?>? {
        return cartRepository.findAll()
    }
}