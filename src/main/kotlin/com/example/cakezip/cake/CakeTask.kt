package com.example.cakezip.cake

import com.example.cakezip.domain.BaseEntity
import javax.persistence.*

@Entity
class CakeTask(
    @Id // 엔티티 어노테이션이랑 짝꿍
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI 한다는 뜻
    val cakeTaskId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "cake_id")
    val cake: Cake?= null,

    @ManyToOne
    @JoinColumn(name = "cake_option_list_id")
    var cakeOptionList: CakeOptionList,

    ) : BaseEntity() {}