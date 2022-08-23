package com.example.cakezip.service

import com.example.cakezip.domain.Review
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.dto.ReviewDto
import com.example.cakezip.dto.ReviewPercentDto
import com.example.cakezip.repository.*
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val shopRepository: ShopRepository,
    private val orderRepository: OrderRepository,
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

    fun getCustomerAllReviews(customer: Customer): List<ReviewDto>? {
        val reviewList: ArrayList<ReviewDto> = ArrayList()
        for (cake in cakeRepository.findByCustomer(customer)) {
            if (reviewRepository.findReviewByCake(cake) != null) {
                val order = orderRepository.findOrdersByCake(cake)
                val review = reviewRepository.findReviewByCake(cake)
                reviewList.add(
                    ReviewDto(
                        review?.reviewTitle,
                        review?.reviewContent,
                        review?.reviewScore,
                        review?.cake?.shop?.shopName,
                        review?.createdAt,
                        order,
                        cake
                    )
                )
            }
        }
        return reviewList
    }

    fun getShopAllReviews(shopId: Long): ArrayList<ReviewDto>? {
        val shop = shopRepository.findByShopId(shopId)
        val reviewList: ArrayList<ReviewDto> = ArrayList()

        for (cake in cakeRepository.findByShop(shop)) {
            val order = orderRepository.findOrdersByCake(cake)
            val review = reviewRepository.findReviewByCake(cake)
            reviewList.add(ReviewDto(review?.reviewTitle, review?.reviewContent, review?.reviewScore, review?.cake?.shop?.shopName, review?.createdAt, order, cake))
        }
        return reviewList
    }

    fun getShopReviewPercent(shopId: Long) : ReviewPercentDto {
        val shop = shopRepository.findByShopId(shopId)
        var totalReviewCount = 0
        var onePoint = 0f
        var twoPoint = 0f
        var threePoint = 0f
        var fourPoint = 0f
        var fivePoint = 0f

        for (cake in cakeRepository.findByShop(shop)) {
            val review = reviewRepository.findReviewByCake(cake)
            if (review != null) {
                when(review.reviewScore) {
                    1 -> onePoint += 1
                    2 -> twoPoint += 1
                    3 -> threePoint += 1
                    4 -> fourPoint += 1
                    5 -> fivePoint += 1
                }
                totalReviewCount += 1
            }
        }

        if(totalReviewCount == 0) {
            return ReviewPercentDto(0f,0f,0f,0f,0f,0f)
        }

        val average = (1*onePoint + 2*twoPoint + 3*threePoint + 4*fourPoint + 5*fivePoint) / totalReviewCount
        val onePercent = (onePoint/totalReviewCount) * 100
        val twoPercent = (twoPoint/totalReviewCount) * 100
        val threePercent = (threePoint/totalReviewCount) * 100
        val fourPercent = (fourPoint/totalReviewCount) * 100
        val fivePercent = (fivePoint/totalReviewCount) * 100
        val reviewPercentDto = ReviewPercentDto(average, onePercent, twoPercent, threePercent, fourPercent, fivePercent)
        return reviewPercentDto
    }
}
