package com.example.cakezip.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

interface UploadStoreImgService {

    fun addNewShopImg(image: MultipartFile, shopId:Long)

    fun getCloudAPI() : String

    fun upload(multipartFile: MultipartFile, dir:String, token:String) : String
}