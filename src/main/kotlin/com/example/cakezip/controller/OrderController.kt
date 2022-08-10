//package com.example.cakezip.controller
//
//import com.example.cakezip.service.OrderService
//import org.springframework.stereotype.Controller
//import org.springframework.ui.Model
//import org.springframework.web.bind.annotation.GetMapping
//
//@Controller
//class OrderController(
//    private val orderService: OrderService,
//    ){
//
//    @GetMapping("/orders")
//    fun getOrderList(model: Model): String {
//        model.addAttribute("orderList", orderService.findAll())
//        return "orders"
//    }
//
////    @GetMapping("/orders")
////    fun getOrderList(model: Model): String {
////        //model.addAttribute("orderList", orderService.findOrderListById(orderListId))
////        return "orders";
////    }
//
//    @GetMapping("/index") //url
//    fun getHome(): String {
//        return "/index"; //반환 html 페이지
//    }
//
//    @GetMapping("/mypage")
//    fun getMyPageView(): String {
//        return "/mypage";
//    }
//
//    @GetMapping("/orderdetail")
//    fun getOrderDetailView(): String {
//        return "orderdetail";
//    }
//
//    @GetMapping("/cart")
//    fun getCartList(model: Model): String {
////        model.addAttribute("cartList", productService.findCartList())
//        return "cart" // product.html 반환
//    }
//}