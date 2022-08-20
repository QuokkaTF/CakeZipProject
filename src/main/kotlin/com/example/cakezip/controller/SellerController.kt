package com.example.cakezip.controller;

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
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
    private val orderService: OrderService,
    private val userService: UserService,
){

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

    @GetMapping("/sellers/orders")
    fun getOrderList(model: Model): String {
        model.addAttribute("cake", cakeService.getSellerCakeList(shop, CakeStatusType.CART))
        return "sellerOrders"
    }

    @GetMapping("/sellers/orders/{cakeId}")
    fun getOrderDetailList(model: Model, @PathVariable cakeId: Long): String {
        val c : Cake = cakeService.findByCakeId(cakeId)
        model.addAttribute("cakedetail", cakeService.getCakeOptionList(c))
        model.addAttribute("customerInfo", userService.getCustomerInfo(c))
        model.addAttribute("statusSelected", cakeService.findByCakeId(cakeId).cakeStatus.toString())
        return "sellerOrderDetail"
    }

    @PutMapping("/sellers/orders/{cakeId}")
    fun updateCakeStatus(model: Model, @PathVariable cakeId: Long, statusCheck: CakeStatusType): String {
        cakeService.updateCakeStatus(cakeId, statusCheck)
        println(statusCheck)
        return "redirect:/sellers/orders/{cakeId}"
    }
}
