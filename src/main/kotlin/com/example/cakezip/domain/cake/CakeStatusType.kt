package com.example.cakezip.domain.cake

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class CakeStatusType(
    val cakeStatusName: String
) {
    CARTTEMP("장바구니대기"), // 장바구니대기
    CART("장바구니"), // 장바구니
    PAYMENT("결제완료"), // 결제 완료
    CANCEL("주문취소"), // 주문 취소
    REJECT("주문거절"), // 주문 거절
    PROCEED("준비중"), // 준비 중
    READY("픽업대기중"), // 픽업 대기
    COMPLETE("픽업완료"), // 픽업 완료
    REVIEW("리뷰작성완료"), // 리뷰 작성 완료
}