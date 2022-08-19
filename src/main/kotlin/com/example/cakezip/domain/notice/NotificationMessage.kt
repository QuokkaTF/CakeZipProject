package com.example.cakezip.domain.notice

enum class NotificationMessage {
    ORDER_NEW, // 새 주문 (판매자에게)
    ORDER_CANCEL, // 구매자가 주문 취소 (판매자에게)
    ORDER_DENIED, // 판매자가 주문 거절 (구매자에게)
    ORDER_ACCEPTED, // 판매자가 주문 수락 (준비중, 픽업대기중) (구매자에게)
    ORDER_READY, // 픽업 준비 완료 (구매자에게)
}