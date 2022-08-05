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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val shopId: Long,

    @OneToOne
    @JoinColumn(name = "seller_id")
    val seller : Seller,

    val shopLatitude : Float,

    val shopLongitude : Float,

    val shopArea : String,

    @NotNull
    val shopName: String,

    @NotNull
    val businessNum: String,

    @NotNull
    val shopPhoneNum: String,

    @NotNull
    val shopEmail: String,

    @NotNull
    val shopAddress: String,

    @NotNull
    val shopImgDescriptionUrl: String,

    @NotNull
    val shop_short_descriptor: String = "",
) : BaseEntity() {}