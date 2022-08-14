package com.example.cakezip.security

import com.example.cakezip.domain.member.User
import com.example.cakezip.repository.UserRepository

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(userEmail: String): UserDetails {
        val user : User = userRepository.findByUserEmail(userEmail) ?: throw UsernameNotFoundException("존재하지 않는 user email 입니다")
        println("user")
        return UserDetailsImpl(user)
    }

}