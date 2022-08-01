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
class LikeList : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI 한다는 뜻
    @Column(name = "like_list_id")
    private val likeListId: Long? = null

    private val status: String? = null
}