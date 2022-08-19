package com.example.cakezip.repository

import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SellerRepository: JpaRepository<Seller, Long> {
    fun findBySellerId(sellerId : Long) : Seller
    fun findSellerByUser(user: User): Seller
}