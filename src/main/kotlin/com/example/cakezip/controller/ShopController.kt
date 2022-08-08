package com.example.cakezip.controller

import com.example.cakezip.dto.NewShopReqDto
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ShopController {
    @GetMapping("/add/shop")
    fun addShop(model: Model):String {
        model.addAttribute("form",NewShopReqDto())
        return "addshop"
    }
}