package com.example.cakezip.service

import com.example.cakezip.config.security.NHNCloudConstants
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.LinkedHashMap

@Service
class UploadStoreImgServiceImpl : UploadStoreImgService {

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


        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA
        headers.accept = Collections.singletonList(MediaType.MULTIPART_FORM_DATA)
        headers.set("X-Auth-Token",token)

        var url = storageId + "/" + containerName + "/" + dir

        var byteArrayResource = ByteArrayResource(multipartFile.bytes)

        var body: MultiValueMap<String, Any> = LinkedMultiValueMap()

        body.add("file",byteArrayResource)

        val entity = HttpEntity<Any>(body, headers)
        RestTemplate().exchange(url, HttpMethod.PUT, entity, Any::class.java)

        return url
    }
}