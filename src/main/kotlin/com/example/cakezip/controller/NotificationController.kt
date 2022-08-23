package com.example.cakezip.controller

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.dto.Message
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class NotificationController(
    private val notificationService: NotificationService,
) {
    val noAccessMessage: Message = Message("접근할 수 없는 페이지입니다.", "/")

    @GetMapping("/notifications")
    fun getNotification(model: Model, session: HttpSession): String {
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer = session.getAttribute("customer") as Customer
            model.addAttribute("notification", notificationService.getCNotifications(customer))
            model.addAttribute("data", Message("", ""))
        } else if (user.userType == UserType.CUSTOMER) {
            val seller = session.getAttribute("seller") as Seller
            model.addAttribute("notification", notificationService.getSNotifications(seller))
            model.addAttribute("data", Message("", ""))
        } else {
            model.addAttribute("data", noAccessMessage)
        }

        return "notification"
    }

    @DeleteMapping("/notification/delete/{noticeId}")
    @ResponseBody
    fun deleteNotification(model: Model, @PathVariable("noticeId") noticeId: Long) {
        notificationService.deleteNotification(noticeId)
    }
}
