package com.example.cakezip.controller


import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.domain.shop.ShopImg
import com.example.cakezip.dto.NewShopReqDto
import com.example.cakezip.dto.ShopDetailInfoDto
import com.example.cakezip.repository.ShopImgRepository
import com.example.cakezip.service.*



import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpSession


@Controller
class ShopController (
    private val shopService: ShopService,
    
    private val shopImgRepository: ShopImgRepository,
    private val shopImgService: ShopImgService,
    private val uploadStoreImgService: UploadStoreImgService,
    private val reviewService: ReviewService

    ){
    @GetMapping("/shops/new")
    fun addShop(model: Model):String {
        //TODO : 사장님 추가
        model.addAttribute("form",NewShopReqDto())
        return "addshop"
    }

    @RequestMapping(value = arrayOf("/shops/new"), method = arrayOf(RequestMethod.POST))
    fun postShop(newShopReqDto: NewShopReqDto) : String{
        shopService.addNewShop(newShopReqDto)
        return "redirect:/shops/new"
    }

    @ResponseBody
    @RequestMapping(value = arrayOf("/image"), method = arrayOf(RequestMethod.POST))
    fun imageShop(@RequestParam images: List<MultipartFile>) : ArrayList<String>{
        var storeUrlList : ArrayList<String> = ArrayList()

        var token:String = uploadStoreImgService.getCloudAPI() // 클라우드 api를 사용하기 위한 곳

        for(image:MultipartFile in images) {
            var url:String = uploadStoreImgService.upload(image, image.originalFilename.toString(), token)
            storeUrlList.add(url)
        }
        return storeUrlList
    }

    @GetMapping("/shops")
    fun shopList(model: Model) : String {
        model.addAttribute("shops",shopService.getAllShopSimpleList())
        return "allshop"
    }

    @GetMapping("/shops/{shopId}")
    fun shopDetail(@PathVariable("shopId") shopId:Long, model:Model, session: HttpSession) : String {
        val user: User = session.getAttribute("user") as User
        var customer : Customer? = null

        if(user.userType == UserType.CUSTOMER) {
            customer = session.getAttribute("customer") as Customer
        } 

        val shopDetail:ShopDetailInfoDto = shopService.getShopDetail(customer, shopId)

        model.addAttribute("customer", customer)
        model.addAttribute("shopInfo",shopDetail)
        
        model.addAttribute("reviewScore", reviewService.getShopReviewPercent(shopId))
        model.addAttribute("reviewDetail", reviewService.getShopAllReviews(shopId))
        

        return "product"
    }

}

    



