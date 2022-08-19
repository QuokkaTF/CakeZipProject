package com.example.cakezip.dto

import com.example.cakezip.domain.Orders

data class NotificationDto(
    val noticeId: Long? = null,
    val order: Orders? = null,
    val noticeMessage: String,
    val noticeType: String,
    ) {
}