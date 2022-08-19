package com.example.cakezip.domain.member

import com.example.cakezip.domain.BaseEntity
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class User (

    @NotNull
    var userEmail: String,

    @NotNull
    var password: String,

    @NotNull
    var phoneNum: String,

    @NotNull
    var userName: String,

    @NotNull
    @Enumerated(value = EnumType.STRING)
    var userType: UserType
    ) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long?=null



    }
