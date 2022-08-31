package com.example.cakezip.controller

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.dto.Message
import com.example.cakezip.repository.ShopRepository
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class LikeController(
    private val likeListService: LikeListService,
    private val shopRepository: ShopRepository,
) {
    val noAccessMessage: Message = Message("접근할 수 없는 페이지입니다.", "/")

    @PostMapping("/like/{shopId}")
    @ResponseBody
    fun shopLike(model: Model, @PathVariable("shopId") shopId: Long, session: HttpSession): Boolean {
        val shop = shopRepository.findByShopId(shopId)
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer = session.getAttribute("customer") as Customer
            model.addAttribute("data", Message("", ""))
            val res = likeListService.addLike(customer, shop)
            session.setAttribute("likeCount",likeListService.getCustomerLikeCount(customer))
            println(res)
            println("--------")
            return res
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return false
    }

    @GetMapping("/likedshop")
    fun likedShopList(model: Model, session: HttpSession): String {
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer = session.getAttribute("customer") as Customer
            if (likeListService.getLikedShopList(customer).isEmpty()) {
                model.addAttribute("data", Message("좋아요한 가게가 아직 존재하지 않습니다.", "/"))
            } else {
                model.addAttribute("shops", likeListService.getLikedShopList(customer))
                model.addAttribute("data", Message("", ""))
            }
        } else {
            model.addAttribute("data", noAccessMessage)
        }

        return "likedShop"
    }
}
