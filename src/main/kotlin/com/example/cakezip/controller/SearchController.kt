package com.example.cakezip.controller

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.dto.ShopDetailInfoDto
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class SearchController(
    private val customerService: CustomerService,
    private val shopService: ShopService,
) {
    @GetMapping("/search")
    fun getCartPaymentList(model: Model, keyword:String, session: HttpSession): String {
        var customer: Customer? = null
        if((session.getAttribute("user") as User).userType == UserType.CUSTOMER) {
            customer = (session.getAttribute("customer") as Customer)
        }

        var searchList : ArrayList<ShopDetailInfoDto> = shopService.searchShop(keyword,customer)
        model.addAttribute("searchList", searchList)
        model.addAttribute("keyword", keyword)
        return "search"
    }
}
