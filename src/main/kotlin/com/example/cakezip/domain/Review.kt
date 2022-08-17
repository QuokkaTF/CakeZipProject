package com.example.cakezip.domain

import com.example.cakezip.domain.cake.Cake
import javax.persistence.*

@Entity
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val reviewId: Long? = null,

    val reviewTitle: String,
    val reviewContent: String,
    val reviewScore: Int,

    @OneToOne
    @JoinColumn(name = "cake_id")
    val cake: Cake,

    ) : BaseEntity() {}
