package com.example.cakezip.domain.member

import com.example.cakezip.domain.BaseEntity
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long?=null,

    var userName: String,

    var userEmail: String,

    var password: String,

    var phoneNum: String,

    @Enumerated(value = EnumType.STRING)
    val userType: UserType,
    ) : BaseEntity() {}
