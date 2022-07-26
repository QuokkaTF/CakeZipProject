package com.example.cakezip.service

import com.example.cakezip.domain.member.Seller
import com.example.cakezip.repository.SellerRepository
import org.springframework.stereotype.Service

@Service
class SellerServiceImpl(private val sellerRepository: SellerRepository) : SellerService {
    override fun findBySellerId(sellerId: Long): Seller {
        return sellerRepository.findBySellerId(sellerId)
    }
}