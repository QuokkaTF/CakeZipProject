package com.example.cakezip.controller

import com.example.cakezip.config.BaseResponse
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserDto
import com.example.cakezip.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpSession

@RequestMapping("/users")
@RestController
class UserController(
    private val userService : UserService) {



    @PostMapping("")
    fun findPasswd(user:String) {

    }
    /**
     * Sign in 회원가입
     *
     * @param userDto
     */
    @PostMapping("")
    fun signIn(userDto: UserDto ) = BaseResponse(userService.createUser(userDto))

    /**
     * Login
     *
     * @param userEmail
     * @param password
     * @param session
     * @return
     * 로그인 성공시 세션 저장
     * 이외에는 에러 메시지 전달
     */

    @PostMapping("/login")
    fun login(@RequestParam userEmail: String, @RequestParam password: String, session: HttpSession) : BaseResponse<String>{
        val res: String = userService.userLogin(userEmail,password)

        if(res != "not_match_password"&& res != "user_not_found") {
            val user: User? = userService.findUser(userEmail)

            if (user != null) {
                session.setAttribute("userId",user.userId)
            }
        }
        return BaseResponse(userService.userLogin(userEmail,password))
    }

    /**
     * Id check 이메일 중복 체크
     *
     * @param userEmail
     */
    @PostMapping("/idcheck")
    fun idCheck(@RequestParam userEmail: String) = BaseResponse(userService.idCheck(userEmail))

}