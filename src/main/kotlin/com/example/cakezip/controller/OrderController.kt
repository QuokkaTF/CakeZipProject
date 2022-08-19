package com.example.cakezip.controller

import com.example.cakezip.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class OrderController(
    private val orderService: OrderService,
    ){

    @GetMapping("/orders")
    fun getOrderList(model: Model): String {
        model.addAttribute("orderList", orderService.findAll())
        println("????????")
        return "orders"
    }

    @GetMapping("/orders/orderdetail/{orderListId}")
    fun getOrderListById(model: Model, @PathVariable("orderListId") orderListId: Long): String {

        model.addAttribute("orderDetail", orderService.getOrderListById(orderListId))
        println("상세 조회")
        return "orderdetail";
    }



    @GetMapping("/index") //url
    fun getHome(): String {
        return "index"; //반환 html 페이지
    }

    @GetMapping("/mypage")
    fun getMyPageView(): String {
        return "mypage";
    }

//    @GetMapping("/orderdetail")
//    fun getOrderDetailView(): String {
//        return "orderdetail";
//    }

    @GetMapping("/cart")
    fun getCartList(model: Model): String {
        return "cart" // product.html 반환
    }

    @GetMapping("/editinfo")
    fun getMyInfoView(): String {
        return "editinfo";
    }

    @GetMapping("/reviews")
    fun getReviewView(): String {
        return "myreview";
    }




}