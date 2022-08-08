package com.example.cakezip.domain.member

import com.example.cakezip.domain.BaseEntity
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class User (
    userEmail: String,
    password: String,
    phoneNum: String,
    userName: String,
    userType: UserType
    ) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long?=null

    var userName: String = userName
    private set

    var userEmail: String = userEmail
    private set

    var password: String = password
    private set

    var phoneNum: String = phoneNum
    private set

    @Enumerated(value = EnumType.STRING)
    var userType: UserType = userType
    private set
    }
