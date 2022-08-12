package com.example.cakezip.controller

import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.CakeTask
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.repository.CustomerRepository
import com.example.cakezip.service.CakeOptionListService
import com.example.cakezip.service.CakeService
import com.example.cakezip.service.CakeTaskService
import com.example.cakezip.service.CustomerService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap

@Controller
class CartController(
    private val customerService: CustomerService,
    private val cakeService: CakeService,
    private val cakeTaskService: CakeTaskService,
    private val cakeOptionListService: CakeOptionListService,

    ) {


    //TODO : 경민한테 받으면 수정
    var customer: Customer = customerService.findByCustomerId(2)

    @GetMapping("/users/cart")
    fun getCartList(model: Model): String {

        var cake : ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
        for (c in cakeService.findByCustomerAndCakeStatus(customer, "장바구니")){
            var totalPrice : Long = 0
            var cake_hashMap = HashMap<String, Any>()
            for (ct in cakeTaskService.findByCake(c)){
                if (ct.cakeOptionList.cakeOptionListId != null) {
                    var cakeOptionList : Optional<CakeOptionList> =
                        cakeOptionListService.findByCakeOptionListId(ct.cakeOptionList.cakeOptionListId!!)
                    cake_hashMap.put(cakeOptionList.get().optionTitle.toString(),cakeOptionList.get().optionDetail)
                    cake_hashMap.put(cakeOptionList.get().optionTitle.toString()+"price",
                        cakeOptionList.get().optionPrice)

                    totalPrice += cakeOptionList.get().optionPrice
                }
            }
            println("_________---_____^^*_______")
            c.cakeId?.let { cake_hashMap.put("id", it) }
            cake_hashMap.put("price",totalPrice)
            cake_hashMap.put("shop",c.shop.shopName)
            cake_hashMap.put("pickupdate",c.pickupDate)
            cake_hashMap.put("letterText",c.letterText)
            cake_hashMap.put("etc",c.etc)

            cake.add(cake_hashMap)
        }
        println("=====================================")
        println(cake)
        model.addAttribute("cake", cake)


        return "cart" // product.html 반환
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
        println("삭제할거야~!~")

        // cake task
        for (c in cakeService.findByCustomerAndCakeStatus(customer, "장바구니")){
            cakeTaskService.deleteAllByCake(c)
        }

        // cake
        cakeService.deleteAllByCustomerAndCakeStatus(customer,"장바구니")
        return "redirect:/users/cart"
    }

}
