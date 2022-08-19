package com.example.cakezip.controller

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class SearchController(
    private val customerService: CustomerService,
    private val shopService: ShopService,
) {
    //TODO : 경민한테 받으면 수정
    var customer: Customer = customerService.findByCustomerId(2)

    @GetMapping("/search")
    fun getCartPaymentList(model: Model, keyword:String): String {
        var searchList = shopService.searchShop(keyword)
        model.addAttribute("searchList", searchList)
        return "search"
    }
}
