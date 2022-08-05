package com.example.cakezip.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ShopController {
    @GetMapping("/add/shop")
    fun addShop():String {
        return "addshop"
    }
}