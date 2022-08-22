package com.example.cakezip.repository

import com.example.cakezip.domain.Review
import com.example.cakezip.domain.cake.Cake
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {
    fun findReviewByCake(cake: Cake): Review?
}
