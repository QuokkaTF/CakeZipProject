package com.example.cakezip.service


import com.example.cakezip.domain.Review
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.repository.ReviewRepository
import org.springframework.stereotype.Service


@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
){

    fun addReview(reviewTitle: String, reviewContent: String, reviewScore: Integer,cake: Cake): Review {
        val review = Review(
            reviewTitle = reviewTitle,
            reviewContent = reviewContent,
            reviewScore = reviewScore,
            cake =cake,
        )
        return reviewRepository.save(review)
    }

}

