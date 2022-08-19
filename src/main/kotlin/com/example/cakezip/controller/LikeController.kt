package com.example.cakezip.controller

import com.example.cakezip.repository.ShopRepository
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class LikeController(
    private val likeListService: LikeListService,
    ) {


    // 가게 좋아요 기능
    @PostMapping("/like/{shopId}")
    @ResponseBody
//    fun shopLike(model: Model, shopId: Long): Int {
    fun shopLike(model: Model, @PathVariable("shopId") shopId: Long): Boolean {
        //val customer = customerService.findByCustomerId(1) // TODO: 나중에 사용자 토큰 받아서 수정
        //val shopId:Long = 1
        //val shop = shopRepository.findByShopId(shopId)

        return likeListService.addLike(3, 1)
    }


}

