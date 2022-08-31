package com.example.cakezip.controller

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.Message
import com.example.cakezip.service.*
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession


@Controller
class MainController(
    private val notificationService: NotificationService,
    private val likeListService: LikeListService,
    private val userService: UserService,
    private val cakeService: CakeService,
    ) {
    val noAccessMessage: Message = Message("접근할 수 없는 페이지입니다.", "/")

    @GetMapping("/home")
    fun getMainView(model: Model, session: HttpSession, @AuthenticationPrincipal user: User?): String {
        if(user != null) {
            val tmp = session.getAttribute("user") as User?
            if(session.getAttribute("user") as User? == null) {
                session.setAttribute("user",user)
                if(user.userType == UserType.CUSTOMER) {
                    session.setAttribute("customer", userService.findCustomerByUser(user))
                } else {
                    session.setAttribute("seller", userService.findSellerByUser(user))
                }
            }

            if(user.userType == UserType.CUSTOMER) {
                val customer = session.getAttribute("customer") as Customer
                model.addAttribute("notification", notificationService.getCNotifications(customer))
                session.setAttribute("likeCount",likeListService.getCustomerLikeCount(customer))
                session.setAttribute("cartCount",cakeService.countCart(customer))
            } else {
                val seller = session.getAttribute("seller") as Seller
                model.addAttribute("notification", notificationService.getSNotifications(seller))
                return "redirect:/sellers/myshop"
            }
        }else {
            session.invalidate()
        }

        return "index"
    }

    @GetMapping("/mypage")
    fun getMyPageView(model: Model, session: HttpSession): String {
        val user: User = session.getAttribute("user") as User

        if (user.userType == UserType.CUSTOMER) {
            model.addAttribute("data", Message("", ""))
        } else if (user.userType == UserType.SELLER) {
            model.addAttribute("data", noAccessMessage)
        }

        return "myPage"
    }
}
