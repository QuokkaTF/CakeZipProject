package com.example.cakezip.controller

import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.dto.UserPaymentDto
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class CartController(
    private val customerService: CustomerService,
    private val cakeService: CakeService,
    private val cakeTaskService: CakeTaskService,
    private val cakeOptionListService: CakeOptionListService,
    private val orderService: OrderService,
    private val shopService: ShopService,
) {
    //TODO : 경민한테 받으면 수정
    var customer: Customer = customerService.findByCustomerId(2)

    @GetMapping("/users/cart")
    fun getCartList(model: Model): String {
        var cake =
            cakeService.getCakeOptionListAll(cakeService.findByCustomerAndCakeStatus(customer, CakeStatusType.CART))
        model.addAttribute("cake", cake)
        return "cart"
    }

    @DeleteMapping("/users/cart/{cakeId}")
    fun deleteCake(@PathVariable cakeId: Long): String {
        // cake task
        cakeTaskService.deleteAllByCake_cakeId(cakeId)
        // cake
        cakeService.deleteAllByCakeId(cakeId)
        return "redirect:/users/cart"
    }

    @DeleteMapping("/users/cart")
    fun deleteAllCake(): String {
        // cake task
        for (c in cakeService.findByCustomerAndCakeStatus(customer, CakeStatusType.CART)) {
            cakeTaskService.deleteAllByCake(c)
        }
        // cake
        cakeService.deleteAllByCustomerAndCakeStatus(customer, CakeStatusType.CART)
        return "redirect:/users/cart"
    }

    @GetMapping("/users/cart/{cakeId}")
    fun getCartPaymentList(model: Model, @PathVariable cakeId: Long): String {
        val c = cakeService.findByCakeId(cakeId)
        model.addAttribute("cake", cakeService.getCakeOptionList(c))
        val userinfo: UserPaymentDto =
            UserPaymentDto(customer.user.userName, customer.user.userEmail, customer.user.phoneNum)
        model.addAttribute("userinfo", userinfo)
        return "payment"
    }

    @PostMapping("/users/cart/{cakeId}")
    fun paymentCake(@PathVariable cakeId: Long, imp_uid: String, cake_id: Long, price: Long): String {
        val orderCake = cakeService.findByCakeId(cake_id)
        orderService.addOrder(imp_uid, price, customer, orderCake)
        cakeService.updateCakeStatus(cakeId, CakeStatusType.PAYMENT)
        return "redirect:/users/cart"
    }

    @PostMapping("/users/cart")
    fun addCart(
        designCheck: Long, sizeCheck: Long, sheetCheck: Long, creamCheck: Long, creamcolorCheck: Long, letterCheck: Long,
        shopId: Long, letterText:String, etc:String, date:String, time:String
    ): String {
        cakeService.addCartCake(
            date+" "+time, letterText, etc,
            0, CakeStatusType.CARTTEMP, shopService.getByShopId(shopId), customer
        )
        val cake = cakeService.findByCustomerAndCakeStatus(customer, CakeStatusType.CARTTEMP)[0]
        val check: LongArray = longArrayOf(designCheck,sizeCheck,sheetCheck,creamCheck,creamcolorCheck,letterCheck)
        for (c in check){
            cakeTaskService.addCartCakeTask(cake, cakeOptionListService.findByCakeOptionListId(c).get())
        }
        cakeService.sumPrice(cake)
        cakeService.updateCakeStatus(cake.cakeId!!, CakeStatusType.CART)
        return "redirect:/users/cart"
    }
}
