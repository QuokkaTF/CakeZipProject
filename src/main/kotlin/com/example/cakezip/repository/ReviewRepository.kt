package com.example.cakezip.repository

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface ReviewRepository : JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r" +
            " WHERE r.cake.customer.customerId=:id")
    fun findReviewByCustomerId(id: Long) : List<Review>?



}