package com.example.cakezip.repository

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.Review
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface ReviewRepository : JpaRepository<Review, Long> {

//    @Query("SELECT r FROM Review r" +
//            " WHERE r.cake.shop.shopId=:id")
//    fun findReviewByShopId(id: Long): List<Review>?

    //fun findReviewByCustomer(customer: Customer): List<Review>?

    fun findReviewByCake(cake: Cake): Review?

    //fun findReviewByShop(shop: Shop): List<Review>?

}