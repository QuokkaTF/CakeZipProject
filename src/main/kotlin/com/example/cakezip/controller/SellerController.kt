package com.example.cakezip.controller;

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.repository.ShopRepository
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Controller
class SellerController (
    private val shopImgService: ShopImgService,
    private val shopService: ShopService,
    private val sellerService: SellerService,
    private val cakeService: CakeService,
    private val cakeTaskService: CakeTaskService,
    private val cakeOptionListService: CakeOptionListService,
    private val orderService: OrderService,){

    @GetMapping("/sellers/myshop/{sellerId}")
    fun sellerMyShop(@PathVariable("sellerId") sellerId:Long, model:Model) : String {

        var seller: Seller = sellerService.findBySellerId(sellerId)
        var shop : Shop? = shopService.getMyShop(seller)
        model.addAttribute("shop", shop)
        if (shop != null) {
            model.addAttribute("shopImgs",shopImgService.getShopImgs(shop))
        }
        return "sellermain"
    }

    @GetMapping("/sellers/myshop/info/{shopId}")
    fun modifySHopInfoPage(@PathVariable("shopId") shopId:Long, model:Model) :String {
        model.addAttribute("shop", shopService.getByShopId(shopId))
        return "editshop"
    }

    @PutMapping("/sellers/myshop/info/{shopId}")
    fun modifyShop(shop: Shop, @PathVariable("shopId") shopId: Long) :String{
        val shop = shopService.updateShopInfo(shopId, shop)
        println(shop)
        return "index"
    }

    @PutMapping("/sellers/myshop/{shopId}")
    fun modifyShop(@PathVariable("shopId") shopId: Long) :String{
        shopService.deleteShop(shopId)
        return "index" // TODO : url 변경 필요
    }

    //TODO : 경민한테 받으면 수정
    var seller: Seller = sellerService.findBySellerId(1)

    var shop: Shop = shopService.findBySeller(seller)
    val cake = cakeService.findByShopAndCakeStatusNot(shop, CakeStatusType.CART)

    @GetMapping("/sellers/orders")
    fun getOrderList(model: Model): String {
        model.addAttribute("cake", cake)
        return "sellerorders"
    }

    @GetMapping("/sellers/orders/{cakeId}")
    fun getOrderDetailList(model: Model, @PathVariable cakeId: Long): String {
        var cakelist: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
        var cake_hashMap = HashMap<String, Any>()
        var totalPrice : Long=0
        val c : Cake = cakeService.findByCakeId(cakeId)

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
        println("_________---_____^^*_______")
        cake_hashMap.put("price", totalPrice)
        cake_hashMap.put("shop", c.shop.shopName)
        cake_hashMap.put("pickupdate", c.pickupDate)
        cake_hashMap.put("letterText", c.letterText)
        cake_hashMap.put("etc", c.etc)

        cakelist.add(cake_hashMap)

        println("=====================================")
        println(cakelist)
        model.addAttribute("cakedetail", cakelist)
        model.addAttribute("cakeId", cakeId)


        val statusSelected = cakeService.findByCakeId(cakeId).cakeStatus
        println("=====================================")
        println(statusSelected)
        println("=====================================")
        model.addAttribute("statusSelected", statusSelected)


        return "sellerorderdetail"
    }

    @PutMapping("/sellers/orders/{cakeId}")
    fun updateCakeStatus(model: Model, @PathVariable cakeId: Long, statusCheck: CakeStatusType): String {
        cakeService.updateCakeStatus(cakeId, statusCheck)
        println(statusCheck)
        return "redirect:/sellers/orders/{cakeId}"
    }
}
