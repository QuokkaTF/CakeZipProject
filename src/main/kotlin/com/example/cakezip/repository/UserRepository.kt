package com.example.cakezip.repository

import com.example.cakezip.domain.member.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findUserByUserEmail(userEmail: String): User?
    fun existsByUserEmail(userEmail: String): Boolean
    fun findByUserNameAndPhoneNum(userName: String, userPhoneNumber: String) : User?
    fun existsUserByPhoneNum(phoneNum: String): Boolean

    fun findUsersByStatusEquals(status: String): List<User>



}