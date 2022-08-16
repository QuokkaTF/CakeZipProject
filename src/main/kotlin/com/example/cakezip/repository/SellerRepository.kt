package com.example.cakezip.repository

import com.example.cakezip.domain.member.Seller
import org.springframework.data.jpa.repository.JpaRepository

interface SellerRepository : JpaRepository<Seller,Long>{


}