package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
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

    private val status: String?=null

}