package com.example.cakezip.controller

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.CakeTask
import com.example.cakezip.domain.cake.OptionTitleType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.UserDto
import com.example.cakezip.repository.CustomerRepository
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import sun.jvm.hotspot.code.CompressedStream.L
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Controller
class CartController(
    private val customerService: CustomerService,
    private val cakeService: CakeService,
    private val cakeTaskService: CakeTaskService,
    private val cakeOptionListService: CakeOptionListService,
    private val orderService: OrderService,
) {


    //TODO : 경민한테 받으면 수정
    var customer: Customer = customerService.findByCustomerId(2)

    @GetMapping("/users/cart")
    fun getCartList(model: Model): String {

        var cake: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
        for (c in cakeService.findByCustomerAndCakeStatus(customer, "CART")) {
            var totalPrice: Long = 0
            var cake_hashMap = HashMap<String, Any>()
            for (ct in cakeTaskService.findByCake(c)) {
                if (ct.cakeOptionList.cakeOptionListId != null) {
                    var cakeOptionList: Optional<CakeOptionList> =
                        cakeOptionListService.findByCakeOptionListId(ct.cakeOptionList.cakeOptionListId!!)
                    cake_hashMap.put(cakeOptionList.get().optionTitle.toString(), cakeOptionList.get().optionDetail)
                    cake_hashMap.put(
                        cakeOptionList.get().optionTitle.toString() + "price",
                        cakeOptionList.get().optionPrice
                    )

                    totalPrice += cakeOptionList.get().optionPrice
                }
            }
            c.cakeId?.let { cake_hashMap.put("id", it) }
            cake_hashMap.put("price", totalPrice)
            cake_hashMap.put("shop", c.shop.shopName)
            cake_hashMap.put("pickupdate", c.pickupDate)
            cake_hashMap.put("letterText", c.letterText)
            cake_hashMap.put("etc", c.etc)
            cake.add(cake_hashMap)
        }
        println(cake)
        model.addAttribute("cake", cake)

        // 결제 userDTO
        val userinfo: UserDto = UserDto(customer.user.userName, customer.user.userEmail, customer.user.phoneNum)
        model.addAttribute("userinfo", userinfo)
        model.addAttribute("username", customer.user.userName)
        model.addAttribute("userid", customer.user.userId)
        model.addAttribute("useremail", customer.user.userEmail)

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
        for (c in cakeService.findByCustomerAndCakeStatus(customer, "CART")) {
            cakeTaskService.deleteAllByCake(c)
        }
        // cake
        cakeService.deleteAllByCustomerAndCakeStatus(customer, "CART")
        return "redirect:/users/cart"
    }

    @GetMapping("/users/cart/{cakeId}")
    fun getCartPaymentList(model: Model, @PathVariable cakeId: Long): String {

        var cake: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
        val c = cakeService.findByCakeId(cakeId)
        var totalPrice: Long = 0
        var cake_hashMap = HashMap<String, Any>()
        for (ct in cakeTaskService.findByCake(c)) {
            if (ct.cakeOptionList.cakeOptionListId != null) {
                var cakeOptionList: Optional<CakeOptionList> =
                    cakeOptionListService.findByCakeOptionListId(ct.cakeOptionList.cakeOptionListId!!)
                cake_hashMap.put(cakeOptionList.get().optionTitle.toString(), cakeOptionList.get().optionDetail)
                cake_hashMap.put(
                    cakeOptionList.get().optionTitle.toString() + "price",
                    cakeOptionList.get().optionPrice
                )

                totalPrice += cakeOptionList.get().optionPrice
            }
        }
        c.cakeId?.let { cake_hashMap.put("id", it) }
        cake_hashMap.put("price", totalPrice)
        cake_hashMap.put("shop", c.shop.shopName)
        cake_hashMap.put("pickupdate", c.pickupDate)
        cake_hashMap.put("letterText", c.letterText)
        cake_hashMap.put("etc", c.etc)
        cake.add(cake_hashMap)

        println(cake)
        model.addAttribute("cake", cake)

        // 결제 userDTO
        val userinfo: UserDto = UserDto(customer.user.userName, customer.user.userEmail, customer.user.phoneNum)
        model.addAttribute("userinfo", userinfo)
        model.addAttribute("username", customer.user.userName)
        model.addAttribute("userid", customer.user.userId)
        model.addAttribute("useremail", customer.user.userEmail)

        return "payment"

    }

    @PostMapping("/users/cart/{cakeId}")
    fun paymentCake(@PathVariable cakeId: Long, imp_uid: String, cake_id: Long, price: Long): String {
        val orderCake = cakeService.findByCakeId(cake_id)
        orderService.addOrder(imp_uid, price, customer, orderCake)
        cakeService.updateCakeStatus(cakeId, "PAYMENT")
        return "redirect:/users/cart"
    }

    //product temp
    @GetMapping("/product")
    fun getProduct(model: Model): String {
        return "product"
    }

    @PostMapping("/users/cart")
    fun addCart(designCheck:String, sizeCheck:String, sheetCheck:String,
                creamCheck:String, creamcolorCheck:String, letterCheck:String,
                shop: Shop
    ): String {
        cakeService.addCartCake("temp", "temp", "temp",
        0, "CARTTEMP", shop, customer)

        val cake = cakeService.findByCustomerAndCakeStatus(customer, "CAKETEMP")

        val optionToAdd : ArrayList<CakeOptionList> = ArrayList<CakeOptionList>()
        optionToAdd.add(cakeOptionListService.findByOptionTitleAndOptioinDetail("DESIGN",designCheck))
        optionToAdd.add(cakeOptionListService.findByOptionTitleAndOptioinDetail("SIZE",sizeCheck))
        optionToAdd.add( cakeOptionListService.findByOptionTitleAndOptioinDetail("SFLAVOR",sheetCheck))
        optionToAdd.add(cakeOptionListService.findByOptionTitleAndOptioinDetail("CFLAVOR",creamCheck))
        optionToAdd.add(cakeOptionListService.findByOptionTitleAndOptioinDetail("CCOLOR",creamcolorCheck))
        optionToAdd.add(cakeOptionListService.findByOptionTitleAndOptioinDetail("LCOLOR",letterCheck))

        for(option in optionToAdd){
            cakeTaskService.addCartCakeTask(cake[0],option)
        }

        cakeService.updateCakeStatus(cake[0].cakeId!!,"CART")
        return "redirect:/users/cart"
    }

}
