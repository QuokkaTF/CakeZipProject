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
    private val cakeService: CakeService,
) {
    @GetMapping("/customers/orders/detail/{cakeId}")
    fun getOrderDetailsByCakeId(model: Model, @PathVariable("cakeId") cakeId: Long): String {
        val cake = cakeService.findByCakeId(cakeId)
        model.addAttribute("cake", cakeService.getCakeOptionList(cake))
        model.addAttribute("detail", orderService.getCustomerOrders(cakeId))
        if (cake == null) {
            model.addAttribute("error", -1)
            throw Exception("잘못된 접근입니다.")
        }

        return "orderdetail"
    }

    @GetMapping("/customers/orders")
    fun getOrdersByCustomer(model: Model, session: HttpSession): String {
        val user: User = session.getAttribute("user") as User

        if (user.userType == UserType.CUSTOMER) {
            val customer = session.getAttribute("customer") as Customer
            model.addAttribute("detail", orderService.getCustomerAllOrders(customer))
        } else {
            model.addAttribute("error", -1)
            throw Exception("잘못된 접근입니다.")
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
        // TODO: 개인/기업 회원 구분 필요
        return "mypage"
    }
}

