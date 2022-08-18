package com.example.cakezip.domain.shop

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Seller
import org.jetbrains.annotations.NotNull
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
data class Shop(
    @NotNull
    var shopName: String,

    @NotNull
    val businessNum: String,

    @NotNull
    var shopPhoneNum: String,

    @NotNull
    var shopAddressMain: String,

    @NotNull
    var shopAddressDetail: String,

    @NotNull
    var shopImgDescriptionUrl: String,

    @NotNull
    var shopShortDescriptor: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val shopId: Long?=null

    @OneToOne
    @JoinColumn(name = "seller_id")
    var seller : Seller? = null

    var shopAddress : String = this.shopAddressMain + " " +this.shopAddressDetail
    var shopArea : String = this.shopAddress.split(" ")[0] + " " + this.shopAddress.split(" ")[1] // 가게의 대략적인 지역 예) 서울시 구로구

}