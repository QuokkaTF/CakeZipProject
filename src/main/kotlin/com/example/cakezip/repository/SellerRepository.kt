package com.example.cakezip.repository

import com.example.cakezip.domain.member.Seller
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SellerRepository: JpaRepository<Seller, Long> {
    fun findBySellerId(sellerId : Long) : Optional<Seller>
}