package com.example.cakezip.controller

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.service.LikeListService
import com.example.cakezip.service.NotificationService
import com.example.cakezip.service.ShopService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpSession


@Controller
class MainController(
    private val notificationService: NotificationService,
    private val likeListService: LikeListService,
    private val shopService: ShopService
    ) {

    @GetMapping("/home")
    fun getMainView(model: Model, session: HttpSession): String {
        return "index"
    }
}