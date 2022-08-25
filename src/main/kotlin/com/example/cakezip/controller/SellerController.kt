package com.example.cakezip.controller;

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.domain.notice.NotificationMessage
import com.example.cakezip.domain.notice.NotificationType
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

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
class SellerController (
    private val shopImgService: ShopImgService,
    private val shopService: ShopService,
    private val sellerService: SellerService,
    private val cakeService: CakeService,
    private val userService: UserService,
    private val uploadStoreImgService: UploadStoreImgService,
    private val notificationService: NotificationService,
    private val orderService: OrderService,
    ){

    val noAccessMessage: Message = Message("접근할 수 없는 페이지입니다.", "/")

    @GetMapping("/sellers/myshop")
    fun sellerMyShop(model:Model, session: HttpSession) : String {
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.SELLER) {
            val seller:Seller = session.getAttribute("seller") as Seller
            var shop : Shop? = shopService.getMyShop(seller)
            model.addAttribute("shop", shop)
            if (shop != null) {
                model.addAttribute("notification", notificationService.getSNotifications(seller))
                model.addAttribute("shopImgs",shopImgService.getShopImgs(shop))
            }
            model.addAttribute("data", Message("",""))
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "sellerMain"
    }

    @GetMapping("/sellers/myshop/info")
    fun modifyShopInfoPage(model:Model,session: HttpSession) :String {
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.SELLER) {
            val seller:Seller = session.getAttribute("seller") as Seller
            var shop : Shop? = shopService.getMyShop(seller)
            model.addAttribute("shop", shopService.getByShopId(shop!!.shopId!!))
            model.addAttribute("data", Message("",""))
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "editShop"
    }

    @PutMapping("/sellers/myshop/info")
    fun modifyShop(modifyShopInfo: Shop, model:Model,session: HttpSession) :String{
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.SELLER) {
            val seller:Seller = session.getAttribute("seller") as Seller
            var shop : Shop? = shopService.getMyShop(seller)
            model.addAttribute("shop", shopService.updateShopInfo(shop!!.shopId!! , modifyShopInfo))
            model.addAttribute("data", Message("",""))
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "redirect:/sellers/myshop/info"
    }

    @GetMapping("/sellers/myshop/image")
    fun shopMainImages(model: Model,session: HttpSession) : String {
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.SELLER) {
            val seller:Seller = session.getAttribute("seller") as Seller
            var shop : Shop? = shopService.getMyShop(seller)

            model.addAttribute("shop", shop)
            model.addAttribute("shopImg", shopImgService.getShopImgs(shop!!))
            model.addAttribute("data", Message("",""))
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "editImage"
    }

    @DeleteMapping("/sellers/myshop/image/{imageId}")
    fun deleteShopImg(@PathVariable("imageId") imageIds:Long, session: HttpSession, model: Model) : String {
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.SELLER) {
            val shopId : Long = shopImgService.deleteImage(imageIds)
            model.addAttribute("data", Message("","/"))
        }else {
            model.addAttribute("data", noAccessMessage)
        }
        return "redirect:/sellers/myshop/image"
    }

    @RequestMapping(value = arrayOf("/sellers/myshop/image/new"), method = arrayOf(RequestMethod.POST))
    fun editImage(@RequestParam image: MultipartFile, @RequestParam shopId : String, model: Model) :String {
        uploadStoreImgService.addNewShopImg(image, shopId.toLong())
        return "redirect:/sellers/myshop/image"
    }

    @PutMapping("/sellers/myshop")
    fun modifyShop(session: HttpSession, model:Model) :String{
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.SELLER) {
            val seller:Seller = session.getAttribute("seller") as Seller
            var shop : Shop? = shopService.getMyShop(seller)

            shopService.deleteShop(shop!!.shopId!!)
            model.addAttribute("data", Message("",""))
        } else {
            model.addAttribute("data", noAccessMessage)
        }

        return "redirect:/sellers/myshop"
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
        model.addAttribute("cakeDetail", cakeService.getCakeOptionList(c))
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

        orderService.cakeStateNoti(cakeId)

        return "redirect:/sellers/orders/{cakeId}"
    }
}
