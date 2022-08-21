package com.example.cakezip.controller

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpSession

@Controller
class OrderController(
    private val orderService: OrderService,
    ){
    @GetMapping("/customers/orders/detail/{cakeId}")
    fun getOrderDetailsByOrderId(model: Model, @PathVariable("cakeId") cakeId: Long): String {
        model.addAttribute("detail", orderService.getCustomerOrders(cakeId))
        return "orderdetail"
    }

    @GetMapping("/customers/orders")
    fun getOrdersByCustomerId(model: Model, session: HttpSession): String {
        val user: User = session.getAttribute("user") as User

        if(user.userType == UserType.CUSTOMER) {
            val customer = session.getAttribute("customer") as Customer
            model.addAttribute("detail", orderService.getCustomerAllOrders(customer))
        }

        return "orders"
    }

    @PostMapping("/orders/{cakeId}")
    fun deleteOrder(model: Model, @PathVariable("cakeId") cakeId: Long): String {
        orderService.changeCakeStateCancel(cakeId)

        return "redirect:/customers/orders/detail/{cakeId}"
    }

    @GetMapping("/mypage")
    fun getMyPageView(): String {
        return "mypage"
    }

}
