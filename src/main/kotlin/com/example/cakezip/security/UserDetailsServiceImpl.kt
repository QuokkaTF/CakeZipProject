package com.example.cakezip.security

import com.example.cakezip.domain.member.User
import com.example.cakezip.repository.UserRepository

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userEmail: String): UserDetails {
        val user = userRepository.findUserByUserEmail(userEmail)
        return user?: throw  UsernameNotFoundException("loadUserByUsername() - cannot find username:$userEmail")
    }

}