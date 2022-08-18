package com.example.cakezip.dto

import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.notice.NoticeType

data class NoticeDto(
    val noticeId: Long? = null,
//    val customerName: String,
//    val sellerName: String,
    val order: Orders? = null,
    //val shop: Shop,
    val noticeMessage: String,
    val noticeType: String,
    ) {
}