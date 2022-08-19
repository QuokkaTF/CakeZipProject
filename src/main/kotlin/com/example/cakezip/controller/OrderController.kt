package com.example.cakezip.controller

import com.example.cakezip.repository.ShopRepository
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@Controller
class OrderController(
    private val orderService: OrderService,
    private val customerService: CustomerService,
    private val likeListService: LikeListService,
    private val shopRepository: ShopRepository,
    private val noticeService: NotificationService,
    ){

    //TODO: 회원 기능 완료되면 url말고 세션 토큰으로 사용자 정보 받아와서 마이페이지부터 따로 띄우기


    @GetMapping("/customers/orders/detail/{cakeId}")
    fun getOrderDetailsByOrderId(model: Model, @PathVariable("cakeId") cakeId: Long): String {
        //// orderId 이용해서 orderdetail 테이블에서 cakeId 찾은 다음 그 cakeId 이용해서 띄우기!!!

        model.addAttribute("detail", orderService.getCustomerOrders(cakeId))
        println("일반 사용자 주문 상세 조회")
        return "orderdetail";
    }

    @GetMapping("/customers/orders/{customerId}")
    fun getOrdersByCustomerId(model: Model, @PathVariable("customerId") customerId: Long): String {
        model.addAttribute("detail", orderService.getCustomerAllOrders(customerId))
        println("일반 사용자 주문 전체 목록")
        return "orders";
    }



    //주문 취소
    @PutMapping("/orders/{cakeId}")
    fun deleteOrder(model: Model, @PathVariable("cakeId") cakeId: Long): String {

        orderService.changeCakeStateCancel(cakeId)

        println("사용자 주문취소")

        return "redirect:/customers/orders/detail/{cakeId}"
    }




    @GetMapping("/index") //url
    fun getHome(model: Model): String {
//        val n = noticeService.getCustomerNotices(1)
//        println(n)

        model.addAttribute("notification", noticeService.getCustomerNotices(2))
        return "index"; //반환 html 페이지
    }

    @GetMapping("/mypage")
    fun getMyPageView(): String {
        return "mypage";
    }

    @GetMapping("/allshop")
    fun getAllShopView(model: Model): String {
        val customer = customerService.findByCustomerId(3)
        val shop = shopRepository.findByShopId(1)

        var like: Boolean = false
        var count: Int = 0
        like = likeListService.isLike(customer,shop)
        count = likeListService.getLikeCount(1)!!

        model.addAttribute("like", like)
        model.addAttribute("count", count)

        return "allshop";

    }

    @GetMapping("/cart")
    fun getCartList(model: Model): String {
        return "cart" // product.html 반환
    }

    @GetMapping("/editinfo")
    fun getMyInfoView(): String {
        return "editinfo";
    }

    @GetMapping("/likedshop")
    fun getLikedShop(model: Model): String {
        return "likedshop" // product.html 반환
    }





}