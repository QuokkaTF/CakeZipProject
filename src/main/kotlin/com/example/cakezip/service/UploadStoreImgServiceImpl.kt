package com.example.cakezip.service

import com.example.cakezip.config.security.NHNCloudConstants
import com.example.cakezip.domain.shop.ShopImg
import com.example.cakezip.repository.ShopImgRepository
import com.example.cakezip.repository.ShopRepository
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpMessageConverterExtractor
import org.springframework.web.client.RequestCallback
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*


@Service
class UploadStoreImgServiceImpl(
    private val shopImgRepository: ShopImgRepository,
    private val shopRepository: ShopRepository
) : UploadStoreImgService {
    override fun addNewShopImg(image: MultipartFile, shopId: Long) {
        var token = getCloudAPI()
        var url = upload(image, image.originalFilename.toString(), token)
        shopImgRepository.save(ShopImg(shopRepository.findByShopId(shopId), url))
    }

    override fun getCloudAPI(): String {
        var url = NHNCloudConstants.GETTOKENURL

        val jsonObject = JSONObject()
        jsonObject.put("tenantId",NHNCloudConstants.TENANTID)
        val subJson = JSONObject()
        subJson.put("username",NHNCloudConstants.USERNAME)
        subJson.put("password",NHNCloudConstants.PASSWORD)

        jsonObject.put("passwordCredentials",subJson)

        val param = JSONObject()
        param.put("auth",jsonObject)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity<Any>(param.toString(), headers)
        val res1 = RestTemplate().postForEntity(url, entity, Any::class.java)

        val resultResponse = res1.body as LinkedHashMap<String, Any>

        val res2 = resultResponse.get("access") as LinkedHashMap<String, Any>

        val res3 = res2.get("token") as LinkedHashMap<String, Any>
        val tokenId = res3.get("id")
        return tokenId.toString()
    }

    fun convert(file: MultipartFile) : Optional<File> {
        var convertFile : File = File(System.getProperty("user.dir")+"/"+file.originalFilename)
        if (convertFile.createNewFile()) {
            val fos: FileOutputStream = FileOutputStream(convertFile)
            fos.write(file.bytes)
            return Optional.of(convertFile)
        }
        return Optional.empty()
    }

    override fun upload(multipartFile: MultipartFile, dir:String, token:String) : String{
        var uploadFile: File = convert(multipartFile).get()
        return upload(multipartFile, uploadFile, dir, token)
    }

    fun upload(multipartFile: MultipartFile, uploadFile: File, dir:String, token:String) : String{
        var fileName:String = uploadFile.name.split(".")[0]+".png"
        var uploadOmgUrl : String = nhnCloud(multipartFile, uploadFile, fileName, token) //TODO : url
        removeNewFile(uploadFile)
        return uploadOmgUrl
    }

    fun removeNewFile(targetFile: File) {
        if(targetFile.delete()) {
            return
        }
    }

    fun nhnCloud(multipartFile: MultipartFile, uploadFile: File, dir: String, token:String) : String {
        var storageId = NHNCloudConstants.STORAGEID
        val containerName = NHNCloudConstants.CONTANIERNAME

        var url = storageId + "/" + containerName + "/" + dir

        val inputStream = BufferedInputStream(multipartFile.inputStream)

        val requestCallback = RequestCallback { request ->
            request.headers.add("X-Auth-Token", token)
            IOUtils.copy(inputStream, request.body)
        }

        // 오버라이드한 RequestCallback을 사용할 수 있도록 설정
        val requestFactory = SimpleClientHttpRequestFactory()
        requestFactory.setBufferRequestBody(false)
        val restTemplate = RestTemplate(requestFactory)

        val responseExtractor = HttpMessageConverterExtractor(
            String::class.java, restTemplate.messageConverters
        )

        // API 호출
        restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor)
        return url
    }
}