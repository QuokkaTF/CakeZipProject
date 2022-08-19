package com.example.cakezip.controller

import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
class NotificationController(
    private val customerService: CustomerService,
    private val reviewService: ReviewService,
    private val cakeService: CakeService,
    private val cakeTaskService: CakeTaskService,
    private val cakeOptionListService: CakeOptionListService,
    private val noticeService: NotificationService,

    ) {

//    @GetMapping(value = ["/subscribe/{id}"], produces = ["text/event-stream"])
//    fun subscribe(
//        @PathVariable id: Long?,
//        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") lastEventId: String?
//    ): SseEmitter? {
//        //return noticeService.subscribe(id, lastEventId)
//    }

    @GetMapping("/notification/{customerId}")
    fun getNotification(model: Model, customerId: Long): String {
        //val customer = customerService.findByCustomerId(customerId)
        //noticeService.getCustomerNotices(customerId)
        model.addAttribute("notification", noticeService.getCustomerNotices(customerId))
        println("개인 회원 전체 알림")
        return "notification";
    }

    @DeleteMapping("/notification/delete/{noticeId}")
    @ResponseBody
    fun deleteNotification(model: Model, @PathVariable("noticeId") noticeId:Long) {
        noticeService.deleteNotice(noticeId)
    }



}

