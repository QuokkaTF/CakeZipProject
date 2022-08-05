package com.example.cakezip.domain.member

import com.example.cakezip.domain.BaseEntity
import javax.persistence.*

@Entity
class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val customerId: Long,

    @OneToOne
    @JoinColumn(name = "user_id")
    val user:User,

    @Enumerated(value = EnumType.STRING)
    val gender: Gender,

    var areas : String,
) : BaseEntity(){}