package com.example.cakezip.domain.member

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.shop.Shop
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class Seller(

    @OneToOne
    @JoinColumn(name = "user_id")
    val user:User,

//    @OneToOne
//    @JoinColumn(name="shop_id")
//    val shop: Shop,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val sellerId: Long?=null

    fun toSellerDto(): SellerDto {
        return SellerDto(user.userEmail,user.password,user.phoneNum,user.userName)
    }
}