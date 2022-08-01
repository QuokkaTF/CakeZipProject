package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.UserType
import lombok.*
import javax.persistence.*

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class CakeOptionList : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cake_option_list_id")
    private val cakeOptionListId: Long? = null

    @Column(name = "option_title")
    private val optionTitle: String? = null

    @Column(name = "option_detail")
    private val optionDetail: String?= null

    @Column(name = "option_price")
    private val optionPrice: Long?= null

    private val status: String?=null

}