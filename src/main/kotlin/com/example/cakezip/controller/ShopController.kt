package com.example.cakezip.controller

import com.example.cakezip.config.BaseResponse
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.NewShopReqDto
import com.example.cakezip.dto.ShopDetailInfoDto
import com.example.cakezip.dto.ShopSimpleInfoDto
import com.example.cakezip.service.ShopService
import com.example.cakezip.service.UploadStoreImgService

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile



@Controller
class ShopController (private val shopService: ShopService, private val uploadStoreImgService: UploadStoreImgService){
    @GetMapping("/add/shop")
    fun addShop(model: Model):String {
        model.addAttribute("form",NewShopReqDto())
        return "addshop"
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

    @RequestMapping(value = arrayOf("/shop"), method = arrayOf(RequestMethod.POST))
    fun postShop(newShopReqDto: NewShopReqDto) : String{
        println(newShopReqDto)
        shopService.addNewShop(newShopReqDto)
        return "redirect:/add/shop" // FIXME : 경로 결정되면 체크하기
    }

    @GetMapping("/myshops/{shopId}")
    fun modifySHopInfoPage(@PathVariable("shopId") shopId:Long, model:Model) :String {
        model.addAttribute("shop", shopService.getByShopId(shopId))
        //model.addAttribute("form", NewShopReqDto())
        return "editshop"
    }

    @PutMapping("/myshops/{shopId}")
    fun modifyShop(@PathVariable("shopId") shopId:Long, shop: Shop) :String{
        println(shopId)
        println("oiasdhfiEURHGFIulA")
        println(shop.shopId)
        println(shop.shopName)
        println(shop.shopShortDescriptor)
        println(shop.shopImgDescriptionUrl)
        return "index"
    }

    @GetMapping("/shops")
    @ResponseBody
    fun shopList() : BaseResponse<List<ShopSimpleInfoDto>> {
        // TODO : html과 연결해야함
        return BaseResponse(shopService.getAllShopSimpleList())
    }

    @GetMapping("/shops/{shopId}")
    @ResponseBody
    fun shopDetail(@PathVariable("shopId") shopId:Long) : BaseResponse<ShopDetailInfoDto> {
        // TODO : html과 연결해야함
        val shopDetail = shopService.getShopDetail(shopId)
        return BaseResponse(shopDetail)
    }
}