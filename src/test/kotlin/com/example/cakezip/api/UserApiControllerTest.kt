package com.example.cakezip.api

import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserDto
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.repository.UserRepository
import com.example.cakezip.security.UserDetailsServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // BeforeAll
class UserApiControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var objectMapper: ObjectMapper
    @Autowired
    lateinit var userDetailsServiceImpl: UserDetailsServiceImpl
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder
    lateinit var testUser: User

    @BeforeAll
    fun beforeAll() {
        testUser = User("benny1020@naver.com",
            passwordEncoder.encode("roopre"),
            "010-7138-2184",
            "roopretelcham",
            UserType.CUSTOMER
        )
        userRepository.save(testUser)

    }

    @Test
    @DisplayName("POST /users/idcheck")
    fun `회원가입` () {

        val userDto: UserDto = UserDto("roopre1020@naver.com", "roopre","010-7138-2184","roopretelcham",UserType.CUSTOMER)
        val memberDtoJson = objectMapper.writeValueAsString(userDto)

        mockMvc.post("/api/members/signup")
        {
            contentType = MediaType.APPLICATION_JSON
            content = memberDtoJson
        }.andExpect {
            status { isCreated() }
        }.andDo { print() }
    }


}