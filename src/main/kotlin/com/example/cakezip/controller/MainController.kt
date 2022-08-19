package com.example.cakezip.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class MainController {

    @GetMapping("/home")
    fun getMainView() = "index"
}