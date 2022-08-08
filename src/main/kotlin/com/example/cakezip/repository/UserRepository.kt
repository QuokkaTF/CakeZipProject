package com.example.cakezip.repository

import com.example.cakezip.domain.member.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByUserEmail(userEmail : String) : User?
    fun existsByUserEmail(userEmail : String) : Boolean



}