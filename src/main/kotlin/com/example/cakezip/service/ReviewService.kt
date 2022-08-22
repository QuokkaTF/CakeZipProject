package com.example.cakezip.service

import com.example.cakezip.domain.Review
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.dto.ReviewDto
import com.example.cakezip.repository.CakeRepository
import com.example.cakezip.repository.CustomerRepository
import com.example.cakezip.repository.ReviewRepository
import com.example.cakezip.repository.ShopRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val customerRepository: CustomerRepository,
    private val shopRepository: ShopRepository,
    private val cakeRepository: CakeRepository,
){
    fun addReview(reviewTitle: String, reviewContent: String, reviewScore: Int, cake: Cake): Review {
        val review = Review(
            reviewTitle = reviewTitle,
            reviewContent = reviewContent,
            reviewScore = reviewScore,
            cake = cake,
        )
        return reviewRepository.save(review)
    }

    fun getCustomerAllReviews(customerId: Long): List<ReviewDto>? {
        val customer = customerRepository.findByCustomerId(customerId)
        val reviewList: ArrayList<ReviewDto> = ArrayList()

        for (cake in cakeRepository.findByCustomer(customer)) {
            val review = reviewRepository.findReviewByCake(cake)
            reviewList.add(ReviewDto(review?.reviewTitle, review?.reviewContent, review?.reviewScore, review?.cake?.shop?.shopName, review?.createdAt))
        }
        return reviewList
    }

    fun getShopAllReviews(shopId: Long): ArrayList<ReviewDto>? {
        val shop = shopRepository.findByShopId(shopId)
        val reviewList: ArrayList<ReviewDto> = ArrayList()

        for (cake in cakeRepository.findByShop(shop)) {
            val review = reviewRepository.findReviewByCake(cake)
            reviewList.add(ReviewDto(review?.reviewTitle, review?.reviewContent, review?.reviewScore, review?.cake?.shop?.shopName, review?.createdAt))
        }
        return reviewList
    }
}
