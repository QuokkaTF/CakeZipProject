package com.example.cakezip.service

import com.example.cakezip.domain.member.UserDto
import com.example.cakezip.repository.UserRepository
import com.example.cakezip.security.JwtUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository : UserRepository,
    private val passwordEncoder : PasswordEncoder,
    private val authenticationManager : AuthenticationManager,
    private val jwtUtils : JwtUtils
) {

    @Transactional
    fun createUser(userDto: UserDto) {
        userDto.password = passwordEncoder.encode(userDto.password)
        userRepository.save(userDto.toEntity())
    }

    fun idCheck(userEmail : String) : Boolean {
        return userRepository.existsByUserEmail(userEmail)
    }

    @Transactional
    fun userLogin(userEmail: String, password: String): String {
        try {
            //인증 시도
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(userEmail,password,null)
            )
        } catch(e: BadCredentialsException) {
            throw BadCredentialsException("로그인 실패")
        }
        // 예외 발생안했으면 인증 성공
        // 토큰 생성해서 전달
        return jwtUtils.createToken(userEmail)
    }
}