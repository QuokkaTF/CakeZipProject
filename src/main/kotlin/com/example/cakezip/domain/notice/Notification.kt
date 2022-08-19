package com.example.cakezip.domain.notice

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.Orders
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import javax.persistence.*

@Entity
class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val noticeId:Long? = null,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: Customer,

    @ManyToOne
    @JoinColumn(name = "seller_id")
    val seller: Seller,

    @ManyToOne
    @JoinColumn(name = "cake_id")
    val order: Orders?= null,

    @Enumerated(value = EnumType.STRING)
    val notificationMessage: NotificationMessage,

    @Enumerated(value = EnumType.STRING)
    val notificationType: NotificationType,
) : BaseEntity(){}