package com.example.cakezip.controller

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap

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
        //TODO:해당 케이크에 대한 정보 출력할 수있도록 보내는거 (장바구니 코드 활용)
        var cake: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
        val c = cakeService.findByCakeId(cakeId)
        var totalPrice: Long = 0
        var cake_hashMap = HashMap<String, Any>()
        for (ct in cakeTaskService.findByCake(c)) {
            if (ct.cakeOptionList.cakeOptionListId != null) {
                var cakeOptionList: Optional<CakeOptionList> =
                    cakeOptionListService.findByCakeOptionListId(ct.cakeOptionList.cakeOptionListId!!)
                cake_hashMap.put(cakeOptionList.get().optionTitle.toString(), cakeOptionList.get().optionDetail)
                cake_hashMap.put(
                    cakeOptionList.get().optionTitle.toString() + "price",
                    cakeOptionList.get().optionPrice
                )

                totalPrice += cakeOptionList.get().optionPrice
            }
        }
        println("_________---_____^^*_______")
        c.cakeId?.let { cake_hashMap.put("id", it) }
        cake_hashMap.put("price", totalPrice)
        cake_hashMap.put("shop", c.shop.shopName)
        cake_hashMap.put("pickupdate", c.pickupDate)
        cake_hashMap.put("letterText", c.letterText)
        cake_hashMap.put("etc", c.etc)
        cake.add(cake_hashMap)

        println("=====================================")
        println(cake)
        model.addAttribute("cake", cake)

        return "review"
    }

    @PostMapping("/reviews/{cakeId}")
    fun addReview(
        @PathVariable cakeId: Long, model: Model,
        reviewTitle: String, reviewContent: String, reviewScore: Int
    ): String {

//    @GetMapping("/reviews/{shopId}")
//    fun getShopReviews(model: Model, @PathVariable("shopId") shopId: Long): String {
//        model.addAttribute("review", reviewService.getReviewByShopId(shopId))
//        println("해당 가게의 리뷰 전체")
//        return "myreview";
//    }


        val cake = cakeService.findByCakeId(cakeId)

        model.addAttribute("cakeId", cakeId)
        reviewService.addReview(reviewTitle, reviewContent, reviewScore, cake)

        return "redirect:/"
    }

}

