package com.example.cakezip.controller

import com.example.cakezip.dto.NewShopReqDto
import com.example.cakezip.service.ShopService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class ShopController (private val shopService: ShopService){
    @GetMapping("/add/shop")
    fun addShop(model: Model):String {
        model.addAttribute("form",NewShopReqDto())
        return "addshop"
    }

    @RequestMapping(value = arrayOf("/add/shop"), method = arrayOf(RequestMethod.POST))
    fun postShop(newShopReqDto: NewShopReqDto) : String{
        println(newShopReqDto)
        var designList : List<String> = newShopReqDto.designList.trim().split(' ')
        println(designList.size)
        shopService.addNewShop(newShopReqDto)
        return "index"
    }

    @GetMapping("/home")
    fun showShop(model: Model): String {
        return "index"
    }
}