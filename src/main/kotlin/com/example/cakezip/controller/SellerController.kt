package com.example.cakezip.controller;

import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.repository.ShopRepository
import com.example.cakezip.service.SellerService
import com.example.cakezip.service.ShopImgService
import com.example.cakezip.service.ShopService
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable

@Controller
class SellerController (private val sellerService: SellerService, private val shopService: ShopService, private val shopImgService: ShopImgService){

    @GetMapping("/sellers/myshop/{sellerId}")
    fun sellerMyShop(@PathVariable("sellerId") sellerId:Long, model:Model) : String {

        var seller: Seller = sellerService.findBySellerBySellerId(sellerId)
        var shop : Shop? = shopService.getMyShop(seller)
        model.addAttribute("shop", shop)
        if (shop != null) {
            model.addAttribute("shopImgs",shopImgService.getShopImgs(shop))
        }
        return "sellermain"
    }

    @GetMapping("/sellers/myorders/{sellerId}")
    fun sellerMyOrder(@PathVariable("sellerId") sellerId:Long, model:Model) : String {
        //TODO : 주문 받으면 여기서 조회해야 함. -> 주문 정보 받은 후 여기서 설정하기
        var seller: Seller = sellerService.findBySellerBySellerId(sellerId)
        var shop : Shop? = shopService.getMyShop(seller)
        println(shop)
        model.addAttribute("shop", shop)
        return "sellermain"
    }

    @GetMapping("/sellers/myinfo/{sellerId}")
    fun sellerMyInfo(@PathVariable("sellerId") sellerId:Long, model:Model) : String {
        var seller: Seller = sellerService.findBySellerBySellerId(sellerId)
        model.addAttribute("user", seller.user)
        return "sellermain"
    }


}
