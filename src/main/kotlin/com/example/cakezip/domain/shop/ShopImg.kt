package com.example.cakezip.domain.shop

import com.example.cakezip.domain.BaseEntity
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class ShopImg() : BaseEntity()  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_img_id")
    private val shop_img_id: Long? = null

    @NotNull
    @Column(name = "shop_img_url")
    private val shop_img_url: String = ""

    private val status: String?=null
}