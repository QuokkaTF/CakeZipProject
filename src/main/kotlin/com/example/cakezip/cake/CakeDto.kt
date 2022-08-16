package com.example.cakezip.cake

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.domain.shop.Shop
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

class CakeDto(
    var pickupDate: String,
    val shop: Shop,
    var cakeOptionDESIGN: String,
    var cakeOptionSIZE: String,
    var cakeOptionSFLAVOR: String,
    var cakeOptionCFLAVOR: String,
    var cakeOptionCCOLOR: String,
    var cakeOptionLCOLOR: String,
)
