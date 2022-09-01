package com.example.cakezip.controller

import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.dto.Message
import com.example.cakezip.dto.ReviewDto
import com.example.cakezip.dto.ShopSimpleInfoDto
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class ReviewController(
    private val reviewService: ReviewService,
    private val cakeService: CakeService,
    ) {
    val noAccessMessage: Message = Message("접근할 수 없는 페이지입니다.", "/")

    @GetMapping("/reviews")
    fun getMyReviews(model: Model, session: HttpSession, @RequestParam(value = "nowPage", defaultValue = "0") nowPage: Int): String {
        val user: User = session.getAttribute("user") as User

        if (user.userType == UserType.CUSTOMER) {
            val customer = session.getAttribute("customer") as Customer
            if (reviewService.getCustomerAllReviews(customer)?.isEmpty() == true) {
                model.addAttribute("data", Message("작성한 리뷰가 아직 존재하지 않습니다.", "/mypage"))
            } else {
                val row = 3
                val list: ArrayList<ReviewDto> = ArrayList()
                val temp = reviewService.getCustomerAllReviews(customer)
                var totalPage = temp?.size?.div(row)

                if((temp?.size!! % row) > 0) {
                    totalPage = totalPage!! + 1
                }

                for (i in nowPage * row until (nowPage * row) + row) {
                    if(i >= temp.size) {
                        break
                    }
                    list.add(temp[i])
                }

                model.addAttribute("nowPage", nowPage)
                model.addAttribute("totalPage", totalPage)
                model.addAttribute("review", list)
                model.addAttribute("data", Message("", ""))
            }
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "myReview"
    }

    @GetMapping("/reviews/shop/{shopId}")
    fun getShopReviews(model: Model, @PathVariable("shopId") shopId: Long): String {
        model.addAttribute("review", reviewService.getShopAllReviews(shopId))
        return "myreview"
    }

    @GetMapping("/reviews/{cakeId}")
    fun directAddProduct(@PathVariable cakeId: Long, model: Model, session: HttpSession): String {
        model.addAttribute("cake", cakeService.getCakeOptionList(cakeService.findByCakeId(cakeId)))
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer: Customer = session.getAttribute("customer") as Customer
            if (cakeService.findByCakeId(cakeId).customer.customerId == customer.customerId) {
                if (cakeService.findByCakeId(cakeId).cakeStatus != CakeStatusType.COMPLETE) {
                    if (cakeService.findByCakeId(cakeId).cakeStatus == CakeStatusType.REVIEW) {
                        model.addAttribute(
                            "data", Message(
                                "이미 작성된 리뷰입니다.",
                                "/customers/orders/detail/${cakeId}"
                            )
                        )

                    } else {
                        model.addAttribute(
                            "data", Message(
                                "픽업 완료한 케이크만 리뷰를 작성할 수 있습니다.",
                                "/customers/orders/detail/${cakeId}"
                            )
                        )
                    }
                } else {
                    model.addAttribute("data", Message("", ""))
                }
            } else {
                model.addAttribute("data", noAccessMessage)
            }
        } else {
            model.addAttribute("data", noAccessMessage)
        }

        return "review"
    }

    @PostMapping("/reviews/{cakeId}")
    fun addReview(
        @PathVariable cakeId: Long, model: Model,
        reviewTitle: String, reviewContent: String, reviewScore: Int
    ): String {
        val cake = cakeService.findByCakeId(cakeId)

        reviewService.addReview(reviewTitle, reviewContent, reviewScore, cake)
        cakeService.updateCakeStatus(cake.cakeId!!, CakeStatusType.REVIEW)

        return "redirect:/reviews"
    }
}
