package com.example.cakezip.controller

import com.example.cakezip.dto.NewShopReqDto
import com.example.cakezip.dto.ShopDetailInfoDto
import com.example.cakezip.service.ReviewService
import com.example.cakezip.service.ShopService
import com.example.cakezip.service.UploadStoreImgService

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile



@Controller
class ShopController (
    private val shopService: ShopService,
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
        return "redirect:/shops/new" // FIXME : 경로 결정되면 체크하기
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
    fun shopDetail(@PathVariable("shopId") shopId:Long, model:Model) : String {
        model.addAttribute("reviewScore", reviewService.getShopReviewPercent(shopId))
        model.addAttribute("reviewDetail", reviewService.getShopAllReviews(shopId))
        model.addAttribute("shopInfo",shopService.getShopDetail(shopId))
        return "product"
    }

}