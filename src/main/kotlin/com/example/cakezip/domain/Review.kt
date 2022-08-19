package com.example.cakezip.domain

import com.example.cakezip.domain.cake.Cake
import lombok.*
import javax.persistence.*

@Entity
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val reviewId: Long? = null,

    val reviewTitle: String?="제목없음",
    val reviewContent: String?="",
    val reviewScore: Integer?,

    @OneToOne
    @JoinColumn(name = "cake_id")
    val cake: Cake,

    ) : BaseEntity() {}
