package com.example.cakezip.domain.notice

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Notice : BaseEntity(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    @NotNull
    private val noticeId: Long = 0

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private val customer: Customer?= null

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private val seller: Seller?= null

    @Column(name = "notice_message")
    private val noticeMessage: String ?=null

    @Column(name = "notice_type")
    @Enumerated(value = EnumType.STRING)
    private val noticeType: NoticeType? = null

    private val status : String ?= null
}