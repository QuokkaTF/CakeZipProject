package com.example.cakezip.service

import com.example.cakezip.domain.OrderDetail
import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.Review
import com.example.cakezip.dto.OrderDto
import com.example.cakezip.repository.OrderDetailRepository
import com.example.cakezip.repository.OrderRepository
import com.example.cakezip.repository.ReviewRepository
import com.querydsl.core.types.Order
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReviewServiceImpl (
    private val reviewRepository: ReviewRepository,
) : ReviewService {
    override fun getReviewByCustomerId(id: Long): List<Review>? {
        return reviewRepository.findReviewByCustomerId(id)
    }


}
