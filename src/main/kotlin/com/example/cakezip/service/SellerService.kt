package com.example.cakezip.service

import com.example.cakezip.domain.member.Seller

interface SellerService {
    fun findBySellerId(sellerId : Long) : Seller
}