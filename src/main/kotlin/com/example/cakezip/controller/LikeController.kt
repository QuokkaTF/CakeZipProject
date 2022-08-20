package com.example.cakezip.controller

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
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
    @PostMapping("/like/{shopId}")
    @ResponseBody
    fun shopLike(model: Model, @PathVariable("shopId") shopId: Long, session: HttpSession): Boolean {
        val shop = shopRepository.findByShopId(shopId)

        val user: User = session.getAttribute("user") as User
        var customer : Customer? = null

        if(user.userType == UserType.CUSTOMER) {
            customer = session.getAttribute("customer") as Customer
        } else {
            var seller = session.getAttribute("seller") as Seller
        }

        return likeListService.addLike(customer!!, shop)
    }


}

