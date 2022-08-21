package com.example.cakezip.controller

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession


@Controller
class NotificationController(
    private val notificationService: NotificationService,
    ) {
    @GetMapping("/notifications")
    fun getNotification(model: Model, customerId: Long, session: HttpSession): String {
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.CUSTOMER) {
            var customer = session.getAttribute("customer") as Customer
            model.addAttribute("notification", notificationService.getCNotifications(customer))
        } else {
            var seller = session.getAttribute("seller") as Seller
            model.addAttribute("notification", notificationService.getSNotifications(seller))
        }

        return "notification";
    }

    @DeleteMapping("/notification/delete/{noticeId}")
    @ResponseBody
    fun deleteNotification(model: Model, @PathVariable("noticeId") noticeId:Long) {
        notificationService.deleteNotification(noticeId)
    }

}


