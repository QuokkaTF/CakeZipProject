package com.example.cakezip.service

import com.example.cakezip.domain.OrderDetail
import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.Review
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.dto.OrderDto

interface ReviewService {


    fun getReviewByCustomerId(id: Long): List<Review>?

}


