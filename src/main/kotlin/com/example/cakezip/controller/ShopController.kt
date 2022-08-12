package com.example.cakezip.controller

import com.example.cakezip.dto.NewShopReqDto
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

    @RequestMapping(value = arrayOf("/add/shop"), method = arrayOf(RequestMethod.POST))
    fun postShop(newShopReqDto: NewShopReqDto) : String{
        println(newShopReqDto)
        shopService.addNewShop(newShopReqDto)
        return "index"
    }
}