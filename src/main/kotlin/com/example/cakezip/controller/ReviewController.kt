package com.example.cakezip.controller

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.dto.Message
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class ReviewController(
    private val customerService: CustomerService,
    private val reviewService: ReviewService,
    private val cakeService: CakeService,
    private val cakeTaskService: CakeTaskService,
    private val cakeOptionListService: CakeOptionListService,
) {

    //TODO : 경민한테 받으면 수정
    var customer: Customer = customerService.findByCustomerId(2)

    @GetMapping("/reviews/customer/{customerId}")
    fun getMyReviews(model: Model, @PathVariable("customerId") customerId: Long): String {
        model.addAttribute("review", reviewService.getCustomerAllReviews(customerId))
        println("해당 사용자의 리뷰 전체 목록")
        return "myreview";
    }

    @GetMapping("/reviews/shop/{shopId}")
    fun getShopReviews(model: Model, @PathVariable("shopId") shopId: Long): String {
        model.addAttribute("review", reviewService.getShopAllReviews(shopId))
        println("해당 가게의 리뷰 전체 목록")
        return "myreview";
    }

    @GetMapping("/reviews/{cakeId}")
    fun directAddProduct(@PathVariable cakeId: Long, model: Model): String {
        val c: Cake = cakeService.findByCakeId(cakeId)
        model.addAttribute("cake", cakeService.getCakeOptionList(c))
        if (cakeService.findByCakeId(cakeId).cakeStatus != CakeStatusType.COMPLETE) {
            if (cakeService.findByCakeId(cakeId).cakeStatus == CakeStatusType.REVIEW) {
                model.addAttribute("data", Message("이미 작성된 리뷰입니다.", "/"))
            } else {
                model.addAttribute("data", Message("리뷰를 작성할 수 없습니다.", "/"))
            }
        } else {
            model.addAttribute("data", Message("", ""))
        }
        return "review"
    }

    @PostMapping("/reviews/{cakeId}")
    fun addReview(
        @PathVariable cakeId: Long, model: Model,
        reviewTitle: String, reviewContent: String, reviewScore: Int
    ): String {
        val cake = cakeService.findByCakeId(cakeId)
        model.addAttribute("cakeId", cakeId)
        reviewService.addReview(reviewTitle, reviewContent, reviewScore, cake)
        cakeService.updateCakeStatus(cake.cakeId!!, CakeStatusType.REVIEW)
        return "redirect:/"
    }
}
