package com.example.cakezip.controller

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.UserPaymentDto

import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

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
        for (c in cakeService.findByCustomerAndCakeStatus(customer, CakeStatusType.CART)) {
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
        val userinfo: UserPaymentDto = UserPaymentDto(customer.user.userName, customer.user.userEmail, customer.user.phoneNum)
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
        for (c in cakeService.findByCustomerAndCakeStatus(customer, CakeStatusType.CART)) {
            cakeTaskService.deleteAllByCake(c)
        }
        // cake
        cakeService.deleteAllByCustomerAndCakeStatus(customer, CakeStatusType.CART)
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
        val userinfo: UserPaymentDto = UserPaymentDto(customer.user.userName, customer.user.userEmail, customer.user.phoneNum)
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
        cakeService.updateCakeStatus(cakeId, CakeStatusType.PAYMENT)
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
        0, CakeStatusType.CARTTEMP, shop, customer)

        val cake = cakeService.findByCustomerAndCakeStatus(customer, CakeStatusType.CARTTEMP)

        val optionToAdd : ArrayList<CakeOptionList> = ArrayList<CakeOptionList>()
        optionToAdd.add(cakeOptionListService.findByOptionTitleAndOptionDetail("DESIGN",designCheck))
        optionToAdd.add(cakeOptionListService.findByOptionTitleAndOptionDetail("SIZE",sizeCheck))
        optionToAdd.add( cakeOptionListService.findByOptionTitleAndOptionDetail("SFLAVOR",sheetCheck))
        optionToAdd.add(cakeOptionListService.findByOptionTitleAndOptionDetail("CFLAVOR",creamCheck))
        optionToAdd.add(cakeOptionListService.findByOptionTitleAndOptionDetail("CCOLOR",creamcolorCheck))
        optionToAdd.add(cakeOptionListService.findByOptionTitleAndOptionDetail("LCOLOR",letterCheck))

        for(option in optionToAdd){
            cakeTaskService.addCartCakeTask(cake[0],option)
        }

        cakeService.updateCakeStatus(cake[0].cakeId!!, CakeStatusType.CART)
        return "redirect:/users/cart"
    }

}
