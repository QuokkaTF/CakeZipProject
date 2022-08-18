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
    var businessNum: String,

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
    val shopId: Long = 0

    @OneToOne
    @JoinColumn(name = "seller_id")
    var seller : Seller? = null

    var shopAddress : String = this.shopAddressMain + " " +this.shopAddressDetail
    var shopArea : String = this.shopAddress.split(" ")[0] + " " + this.shopAddress.split(" ")[1] // 가게의 대략적인 지역 예) 서울시 구로구

    fun update(shopName: String, businessNum: String,shopPhoneNum: String, shopAddressMain: String, shopAddressDetail: String, shopImgDescriptionUrl: String, shopShortDescriptor: String) {
        this.shopName = shopName
        this.businessNum = businessNum
        this.shopPhoneNum = shopPhoneNum
        this.shopAddressMain = shopAddressMain
        this.shopAddressDetail = shopAddressDetail
        this.shopImgDescriptionUrl = shopImgDescriptionUrl
        this.shopShortDescriptor = shopShortDescriptor
        this.shopAddress = shopAddressMain + " " + shopAddressDetail
        this.shopArea = this.shopAddress.split(" ")[0] + " " + this.shopAddress.split(" ")[1]
    }
}