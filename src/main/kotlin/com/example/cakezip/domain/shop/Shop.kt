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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Shop : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    @NotNull
    private val shopId: Long = 0

    @OneToOne
    @JoinColumn(name = "seller_id")
    private val seller : Seller? = null

    @Column(name = "shop_latitude")
    private val shop_latitude : Float? = null

    @Column(name = "shop_longitude")
    private val shop_longitude : Float? = null

    @Column(name = "shop_area")
    private val shop_area : String? = null

    @Column(name = "shop_name")
    @NotNull
    private val shop_name: String = ""

    @Column(name = "business_num")
    @NotNull
    private val business_num: String = ""

    @Column(name = "shop_phone_num")
    @NotNull
    private val shop_phone_num: String = ""

    @Column(name = "shop_email")
    @NotNull
    private val shop_email: String = ""

    @Column(name = "shop_address")
    @NotNull
    private val shop_address: String = ""

    @Column(name = "shop_img_description_url")
    @NotNull
    private val shop_img_description_url: String = ""

    @Column(name = "shop_short_descriptor")
    @NotNull
    private val shop_short_descriptor: String = ""

    private val status: String?=null

}