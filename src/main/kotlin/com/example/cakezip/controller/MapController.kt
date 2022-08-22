package com.example.cakezip.controller

import com.example.cakezip.dto.ShopAddressDto
import com.example.cakezip.service.MapService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/map")
@Controller
class MapController(private val mapService: MapService) {

    @GetMapping("")
    fun getMapView() =  "map"

    @GetMapping("/list")
    fun getMapList() = ResponseEntity.ok(mapService.findAllShop())
}