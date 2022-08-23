package com.example.cakezip.controller

import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.domain.notice.NotificationMessage
import com.example.cakezip.domain.notice.NotificationType
import com.example.cakezip.dto.Message
import com.example.cakezip.dto.UserPaymentDto
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class CartController(
    private val customerService: CustomerService,
    private val cakeService: CakeService,
    private val cakeTaskService: CakeTaskService,
    private val cakeOptionListService: CakeOptionListService,
    private val orderService: OrderService,
    private val shopService: ShopService,
    private val notificationService: NotificationService,
) {
    val noAccessMessage: Message = Message("접근할 수 없는 페이지입니다.", "/")

    @GetMapping("/users/cart")
    fun getCartList(model: Model, session: HttpSession): String {
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer: Customer = session.getAttribute("customer") as Customer
            var cake =
                cakeService.getCakeOptionListAll(cakeService.findByCustomerAndCakeStatus(customer, CakeStatusType.CART))
            model.addAttribute("cake", cake)
            model.addAttribute("data", Message("", ""))
        } else {
            model.addAttribute("data", Message("접근할 수 없는 페이지입니다.", "/"))
        }
        return "cart"
    }

    @DeleteMapping("/users/cart/{cakeId}")
    fun deleteCake(@PathVariable cakeId: Long, session: HttpSession, model: Model): String {
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer: Customer = session.getAttribute("customer") as Customer
            if (cakeService.findByCakeId(cakeId).customer.customerId == customer.customerId) {
                model.addAttribute("data", Message("", ""))
                cakeTaskService.deleteAllByCake_cakeId(cakeId)
                cakeService.deleteAllByCakeId(cakeId)
            } else {
                model.addAttribute("data", noAccessMessage)
            }
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "redirect:/users/cart"
    }

    @DeleteMapping("/users/cart")
    fun deleteAllCake(model: Model, session: HttpSession): String {
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer: Customer = session.getAttribute("customer") as Customer
            for (c in cakeService.findByCustomerAndCakeStatus(customer, CakeStatusType.CART)) {
                cakeTaskService.deleteAllByCake(c)
            }
            cakeService.deleteAllByCustomerAndCakeStatus(customer, CakeStatusType.CART)
            model.addAttribute("data", Message("", ""))
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "redirect:/users/cart"
    }

    @GetMapping("/users/cart/{cakeId}")
    fun getCartPaymentList(model: Model, @PathVariable cakeId: Long, session: HttpSession): String {
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer: Customer = session.getAttribute("customer") as Customer
            if (cakeService.findByCakeId(cakeId).customer.customerId == customer.customerId) {
                model.addAttribute("data", Message("", ""))
                model.addAttribute("cake", cakeService.getCakeOptionList(cakeService.findByCakeId(cakeId)))
                println(cakeService.findByCakeId(cakeId))
                val userinfo: UserPaymentDto =
                    UserPaymentDto(customer.user.userName, customer.user.userEmail, customer.user.phoneNum)
                model.addAttribute("userinfo", userinfo)
            } else {
                model.addAttribute("data", noAccessMessage)
            }
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "payment"
    }

    @PostMapping("/users/cart/{cakeId}")
    fun paymentCake(
        @PathVariable cakeId: Long, imp_uid: String, cake_id: Long,
        price: Long, session: HttpSession, model: Model
    ): String {
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer: Customer = session.getAttribute("customer") as Customer
            if (cakeService.findByCakeId(cakeId).customer.customerId == customer.customerId) {
                model.addAttribute("data", Message("", ""))
                val orderCake = cakeService.findByCakeId(cake_id)
                orderService.addOrder(imp_uid, price, customer, orderCake)
                cakeService.updateCakeStatus(cakeId, CakeStatusType.PAYMENT)

                notificationService.makeNotification(
                    orderCake.customer.customerId, orderCake.shop.seller!!.sellerId!!,
                    orderService.findByCake(orderCake)!!,
                    NotificationMessage.ORDER_NEW, NotificationType.TOSELLER
                )

            } else {
                model.addAttribute("data", noAccessMessage)
            }
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "redirect:/customers/orders/detail/{cakeId}"
    }

    @PostMapping("/users/cart")
    fun addCart(
        designCheck: Long,
        sizeCheck: Long,
        sheetCheck: Long,
        creamCheck: Long,
        creamcolorCheck: Long,
        letterCheck: Long,
        shopId: Long,
        letterText: String,
        etc: String,
        date: String,
        time: String,
        session: HttpSession,
        model: Model
    ): String {
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer: Customer = session.getAttribute("customer") as Customer
            model.addAttribute("data", Message("", ""))
            cakeService.addCartCake(
                date + " " + time, letterText, etc,
                0, CakeStatusType.CARTTEMP, shopService.getByShopId(shopId), customer
            )
            val cake = cakeService.findByCustomerAndCakeStatus(customer, CakeStatusType.CARTTEMP)[0]
            val check: LongArray = longArrayOf(designCheck, sizeCheck, sheetCheck, creamCheck, creamcolorCheck, letterCheck)
            for (c in check) {
                cakeTaskService.addCartCakeTask(cake, cakeOptionListService.findByCakeOptionListId(c).get())
            }
            cakeService.sumPrice(cake)
            cakeService.updateCakeStatus(cake.cakeId!!, CakeStatusType.CART)
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "redirect:/users/cart"
    }
}
