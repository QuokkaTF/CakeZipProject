package com.example.cakezip.service

import com.example.cakezip.domain.member.CustomerDto
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserDto
import com.example.cakezip.repository.CustomerRepository
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
    private val passwordEncoder : PasswordEncoder,
    private val authenticationManager : AuthenticationManager,
    private val jwtUtils : JwtUtils
) {

    /**
     * Find user email
     *
     * @param userName
     * @param userPhoneNum
     * @return 해당하는 user 존재할시 email
     *
     */
    fun findUserEmail(userName: String, userPhoneNum: String) : String? {
        return userRepository.findByUserNameAndPhoneNum(userName,userPhoneNum)?.userEmail
    }


    /**
     * Find user
     *
     * @param userEmail
     * @return user object or null
     */
    fun findUser(userEmail: String): User? {
        return userRepository.findByUserEmail(userEmail)
    }

    /**
     * 유저 비밀번호 재설정
     *
     * @param user
     * @param userPassword
     * @return void
     *
     * 암호화해서 설정 및 데이터베이스 저장
     */

    fun setUserPassword(user: User, userPassword: String) {
        user.password = passwordEncoder.encode(userPassword)
        userRepository.save(user)
    }

    /**
     * Secession user
     *
     * @param user
     * user status를 secession으로 설정
     * 2년뒤 삭제 예정
     * 중복 불가
     *
     */
    fun secessionUser(user: User) {
        user.status = "secession"
        userRepository.save(user)
    }

    /**
     * 유저 이메일과 이름 일치하는지 확인
     *
     * @param userName
     * @param userEmail
     * @return
     * userEmail의 계정의 이름과 일치한다면 User 리턴
     * 그 외에 false
     */

    fun validateUserEmailAndName(userName: String, userEmail: String): User? {
        return userRepository.findByUserEmail(userEmail)
    }

    /**
     * Exists user
     *
     * @param userEmail
     * @return Exists User true or false
     */
    fun existsUser(userEmail: String): Boolean {
        return userRepository.existsByUserEmail(userEmail)
    }

    /**
     * Create user
     * 회원가입
거    *
     * @param userDto
     * @param customerDto
     *
     * user랑 customer dto 받아서 데이터베이스 저장
     */
    @Transactional
    fun createUser(userDto: UserDto, customerDto: CustomerDto) {
        userDto.password = passwordEncoder.encode(userDto.password)
        customerRepository.save(customerDto.toEntity(userRepository.save(userDto.toEntity())))

    }


    /**
     * User login
     *
     * @param userEmail
     * @param password
     * @return
     * not_match_password 인 경우 비밀번호 틀림
     * user_not_found 인 경우 이메일이 존재하지 않음
     * 그 이외의 경우 토큰 리턴
     */



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