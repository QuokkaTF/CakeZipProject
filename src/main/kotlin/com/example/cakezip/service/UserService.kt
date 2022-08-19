package com.example.cakezip.service

import com.example.cakezip.domain.member.*
import com.example.cakezip.repository.CustomerRepository
import com.example.cakezip.repository.SellerRepository
import com.example.cakezip.repository.UserRepository
import com.example.cakezip.security.JwtUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository : UserRepository,
    private val customerRepository: CustomerRepository,
    private val sellerRepository: SellerRepository,
    private val passwordEncoder : PasswordEncoder,
    private val authenticationManager : AuthenticationManager,
    private val jwtUtils : JwtUtils
) {

    fun findSellerByUser(user: User) = sellerRepository.findSellerByUser(user)

    fun findUserEmail(userName: String, userPhoneNum: String) : String? {
        return userRepository.findByUserNameAndPhoneNum(userName,userPhoneNum)?.userEmail
    }

    fun findUser(userEmail: String): User? {
        return userRepository.findUserByUserEmail(userEmail)
    }

    fun setUserPassword(user: User, userPassword: String) {
        user.password = passwordEncoder.encode(userPassword)
        userRepository.save(user)
    }

    fun secessionUser(user: User) {
        user.status = "secession"
        userRepository.save(user)
    }

    fun validateUserEmailAndName(userName: String, userEmail: String): User? {
        val user: User? = userRepository.findUserByUserEmail(userEmail)
        if (user != null) {
            if(user.userName == userName)
                return user
        }
        return null
    }

    fun existsUser(userEmail: String): Boolean {
        return userRepository.existsByUserEmail(userEmail)
    }

    @Transactional
    fun createCustomer(userDto: UserDto, customerDto: CustomerDto) {
        userDto.password = passwordEncoder.encode(userDto.password)
        val user: User = userDto.toEntity(UserType.CUSTOMER)
        customerRepository.save(customerDto.toEntity(userRepository.save(user)))

    }

    fun createSeller(userDto: UserDto) {
        userDto.password = passwordEncoder.encode(userDto.password)
        val user: User = userDto.toEntity(UserType.SELLER)
        val seller: Seller = Seller(userRepository.save(user))
        sellerRepository.save(seller)
    }

    @Transactional
    fun userLogin(userEmail: String, password: String): String {
        val user: User? = findUser(userEmail)
        if (user != null) {
            if(!passwordEncoder.matches(password,user.password)) {
                return "0"
            }
            else {
                return jwtUtils.createToken(userEmail)
            }
        } else {
            return "-1"
        }

        /* 기존 코드
        try {
            //인증 시도
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(userEmail,password,null)
            )
        } catch(e: BadCredentialsException) {
            println("fail")
            throw BadCredentialsException("로그인 실패")

        }
        println("success")
        // 예외 발생안했으면 인증 성공
        // 토큰 생성해서 전달
        return jwtUtils.createToken(userEmail)
    }
    */
    }

}