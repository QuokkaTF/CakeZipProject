package com.example.cakezip.controller;

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.Message
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import java.util.NoSuchElementException
import javax.servlet.http.HttpSession
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

    @GetMapping("/sellers/orders")
    fun getOrderList(model: Model, session: HttpSession): String {
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.CUSTOMER) {
            model.addAttribute("data", Message("접근할 수 없는 페이지입니다.", "/"))
        } else {
            val seller: Seller = session.getAttribute("seller") as Seller
            try {
                model.addAttribute(
                    "cake",
                    cakeService.getSellerCakeList(shopService.findBySeller(seller), CakeStatusType.CART)
                )
                model.addAttribute("data", Message("", ""))
            } catch (e: NoSuchElementException){
                model.addAttribute("data", Message("가게 등록이 되지 않았습니다.", "/"))
            }
        }
        return "sellerOrders"
    }

    @GetMapping("/sellers/orders/{cakeId}")
    fun getOrderDetailList(model: Model, @PathVariable cakeId: Long, session: HttpSession): String {
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.CUSTOMER) {
            model.addAttribute("data", Message("접근할 수 없는 페이지입니다.", "/"))
        } else {
            val seller: Seller = session.getAttribute("seller") as Seller
            model.addAttribute("data", Message("", ""))
        }
        val c : Cake = cakeService.findByCakeId(cakeId)
        model.addAttribute("cakedetail", cakeService.getCakeOptionList(c))
        model.addAttribute("customerInfo", userService.getCustomerInfo(c))
        model.addAttribute("statusSelected", cakeService.findByCakeId(cakeId).cakeStatus.toString())
        return "sellerOrderDetail"
    }

    @PutMapping("/sellers/orders/{cakeId}")
    fun updateCakeStatus(model: Model, @PathVariable cakeId: Long, statusCheck: CakeStatusType,
                         session: HttpSession): String {
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.CUSTOMER) {
            model.addAttribute("data", Message("접근할 수 없는 페이지입니다.", "/"))
        } else {
            val seller: Seller = session.getAttribute("seller") as Seller
            model.addAttribute("data", Message("", ""))
        }
        cakeService.updateCakeStatus(cakeId, statusCheck)
        println(statusCheck)
        return "redirect:/sellers/orders/{cakeId}"
    }
}
