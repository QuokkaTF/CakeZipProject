package com.example.cakezip.controller

import com.example.cakezip.domain.OrderDetail
import com.example.cakezip.service.OrderServiceImpl
import com.example.cakezip.service.OrderService
import com.example.cakezip.service.ReviewService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@Controller
class ReviewController(
    private val reviewService: ReviewService,
    ){

    //    @GetMapping("/reviews/{cakeId}")
//    fun getReviewView(model: Model, @PathVariable("cakeId") cakeId: Long): String {
//        return "/review";
//    }

    @GetMapping("/reviews/{customerId}")
    fun getMyReviews(model: Model, @PathVariable("customerId") customerId: Long): String {
        model.addAttribute("review", reviewService.getReviewByCustomerId(customerId))
        println("해당 사용자의 리뷰 전체 목록")
        return "myreview";
    }


}