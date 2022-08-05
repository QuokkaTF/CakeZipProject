package com.example.cakezip.controller

import com.example.cakezip.service.CartService
import com.example.cakezip.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDateTime

@Controller
class CartController(
    private val cartService: CartService,
    private val orderService: OrderService,
) {

    @GetMapping("/users/cart")
    fun getCartList(model: Model): String {
        model.addAttribute("cartList", cartService.findAll())
        return "cart" // product.html 반환
    }

    @GetMapping("/order")
    fun getOrderList(model: Model): String {
        model.addAttribute("orderList", orderService.findAll())
        return "orders"
    }
}
