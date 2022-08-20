package com.example.cakezip.controller;

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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
    private val uploadStoreImgService: UploadStoreImgService,
    ){

    @GetMapping("/sellers/myshop/{sellerId}")
    fun sellerMyShop(@PathVariable("sellerId") sellerId:Long, model:Model) : String {
        var seller: Seller = sellerService.findBySellerId(sellerId)
        var shop : Shop? = shopService.getMyShop(seller)
        model.addAttribute("shop", shop)
        if (shop != null) {
            model.addAttribute("shopImgs",shopImgService.getShopImgs(shop))
        }
        return "sellerMain"
    }

    @GetMapping("/sellers/myshop/info/{shopId}")
    fun modifySHopInfoPage(@PathVariable("shopId") shopId:Long, model:Model) :String {
        model.addAttribute("shop", shopService.getByShopId(shopId))
        return "editShop"
    }

    @PutMapping("/sellers/myshop/info/{shopId}")
    fun modifyShop(shop: Shop, @PathVariable("shopId") shopId: Long) :String{
        val shop = shopService.updateShopInfo(shopId, shop)
        return "redirect:/sellers/myshop/info/$shopId"
    }

    @GetMapping("/sellers/myshop/image/{shopId}")
    fun shopMainImages(@PathVariable("shopId") shopId:Long, model: Model) : String {
        val shop : Shop = shopService.getByShopId(shopId)
        model.addAttribute("shop", shop)
        model.addAttribute("shopImg", shopImgService.getShopImgs(shop))
        return "editImage"
    }

    @DeleteMapping("/sellers/myshop/image/{imageId}")
    fun deleteShopImg(@PathVariable("imageId") imageIds:Long) : String {
        val shopId : Long = shopImgService.deleteImage(imageIds)
        return "redirect:/sellers/myshop/image/$shopId"
    }

    @RequestMapping(value = arrayOf("/sellers/myshop/image/new"), method = arrayOf(RequestMethod.POST))
    fun editImage(@RequestParam image: MultipartFile, @RequestParam shopId : String, model: Model) :String {
        uploadStoreImgService.addNewShopImg(image, shopId.toLong())
        return "redirect:/sellers/myshop/image/$shopId"
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
