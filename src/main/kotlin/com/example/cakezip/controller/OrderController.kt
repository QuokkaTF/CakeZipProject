package com.example.cakezip.controller

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.dto.Message
import com.example.cakezip.dto.OrderDto
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpSession

@Controller
class OrderController(
    private val orderService: OrderService,
    private val cakeService: CakeService,
) {
    val noAccessMessage: Message = Message("접근할 수 없는 페이지입니다.", "/")

    @GetMapping("/customers/orders/detail/{cakeId}")
    fun getOrderDetailsByCakeId(model: Model, session: HttpSession, @PathVariable("cakeId") cakeId: Long): String {
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer: Customer = session.getAttribute("customer") as Customer
            if (cakeService.findByCakeId(cakeId).customer.customerId == customer.customerId) {
                val cake = cakeService.findByCakeId(cakeId)
                model.addAttribute("cake", cakeService.getCakeOptionList(cake))
                model.addAttribute("detail", orderService.getCustomerOrders(cakeId))
                model.addAttribute("data", Message("", ""))
            } else {
                model.addAttribute("data", noAccessMessage)
            }
        } else {
            model.addAttribute("data", noAccessMessage)
        }

        return "orderDetail"
    }

    @GetMapping("/customers/orders")
    fun getOrdersByCustomer(model: Model, session: HttpSession, @RequestParam(value = "nowPage", defaultValue = "0") nowPage: Int): String {
        val user: User = session.getAttribute("user") as User

        if (user.userType == UserType.CUSTOMER) {
            val customer = session.getAttribute("customer") as Customer
            if (orderService.getCustomerAllOrders(customer)!!.isEmpty()) {
                model.addAttribute("data", Message("주문 내역이 존재하지 않습니다.", "/mypage"))
            } else {
                val row = 3
                val list: ArrayList<OrderDto> = ArrayList()
                val temp = orderService.getCustomerAllOrders(customer)
                var totalPage = temp?.size?.div(row)

                if((temp?.size!! % row) > 0) {
                    totalPage = totalPage!! + 1
                }

                for (i in nowPage * row..(nowPage * row) + row - 1) {
                    if(i >= temp.size) {
                        break
                    }
                    list.add(temp[i])
                }
                model.addAttribute("nowPage", nowPage)
                model.addAttribute("totalPage", totalPage)
                model.addAttribute("detail", list)
                model.addAttribute("data", Message("", ""))
            }
        } else {
            model.addAttribute("data", noAccessMessage)
        }

        return "orders"
    }


    @PostMapping("orders/{cakeId}")
    fun deleteOrder(model: Model, session: HttpSession, @PathVariable("cakeId") cakeId: Long): String {
        val user: User = session.getAttribute("user") as User

        if (user.userType == UserType.CUSTOMER) {
            val customer: Customer = session.getAttribute("customer") as Customer
            if (cakeService.findByCakeId(cakeId).customer.customerId == customer.customerId) {
                if (orderService.changeCakeStateCancel(cakeId) == -1) {
                    model.addAttribute("data", Message("이미 취소된 주문입니다.", ""))
                } else if (orderService.changeCakeStateCancel(cakeId) == -2) {
                    model.addAttribute("data", Message("이미 거절된 주문입니다.", ""))
                } else if (orderService.changeCakeStateCancel(cakeId) == -3) {
                    model.addAttribute("data", Message("이미 진행중인 주문은 취소할 수 없습니다.", ""))
                } else {
                    orderService.changeCakeStateCancel(cakeId)
                }
            } else {
                model.addAttribute("data", noAccessMessage)
            }
        } else {
            model.addAttribute("data", noAccessMessage)
        }

        return "redirect:/customers/orders/detail/{cakeId}"
    }


}
