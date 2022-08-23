package com.example.cakezip.controller


import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.domain.shop.ShopImg
import com.example.cakezip.dto.Message
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
    private val sellerService: SellerService,
    private val shopImgService: ShopImgService,
    private val uploadStoreImgService: UploadStoreImgService,
    private val reviewService: ReviewService

    ){

    val noAccessMessage: Message = Message("접근할 수 없는 페이지입니다.", "/")

    @GetMapping("/shops/new")
    fun addShop(model: Model, session: HttpSession):String {
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.SELLER) {
            model.addAttribute("form",NewShopReqDto())
            model.addAttribute("data", Message("", ""))
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "addshop"
    }

    @RequestMapping(value = arrayOf("/shops/new"), method = arrayOf(RequestMethod.POST))
    fun postShop(newShopReqDto: NewShopReqDto, session: HttpSession, model: Model) : String{
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.SELLER) {
            val seller:Seller = session.getAttribute("seller") as Seller
            shopService.addNewShop(newShopReqDto, seller)
            model.addAttribute("data", Message("", ""))
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "redirect:/sellers/myshop"
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

    



