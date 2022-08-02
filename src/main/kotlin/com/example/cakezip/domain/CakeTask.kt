package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import lombok.*
import javax.persistence.*

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class CakeTask : BaseEntity() {
    @Id // 엔티티 어노테이션이랑 짝꿍
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI 한다는 뜻
    @Column(name = "cake_task_id")
    private val cakeTaskId: Long? = null

    @ManyToOne
    @JoinColumn(name = "cake_id")
    private val cake: Cake?= null

    @ManyToOne
    @JoinColumn(name = "cake_option_list_id")
    private val cakeOptionList: CakeOptionList?= null

    private val status: String?=null

}