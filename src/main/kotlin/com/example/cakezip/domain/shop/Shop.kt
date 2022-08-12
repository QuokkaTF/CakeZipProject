package com.example.cakezip.domain.shop

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Seller
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
class Shop(
    @NotNull
    var shopName: String,

    @NotNull
    val businessNum: String,

    @NotNull
    var shopPhoneNum: String,

    @NotNull
    var shopAddress: String, // 가게 상세 지역 (번지수 까지 자세하게)

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

    var shopArea : String = this.shopAddress.split(" ")[0] + " " + this.shopAddress.split(" ")[1] // 가게의 대략적인 지역 예) 서울시 구로구

//    constructor(shopName: String, businessNum: String, shopPhoneNum: String, shopArea: String, shopAddress: String, shopShortDescriptor: String) : this() {
//        this.shopName = shopName
//        this.businessNum = businessNum
//        this.shopArea =
//    }
}