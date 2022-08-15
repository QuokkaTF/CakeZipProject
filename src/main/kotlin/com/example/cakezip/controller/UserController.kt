package com.example.cakezip.controller

import com.example.cakezip.config.BaseResponse
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserDto
import com.example.cakezip.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
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

    /**
     * Find user email
     *
     * @param userName
     * @param userPhoneNum
     * @return
     * 성공시
     * userEmail
     * 실패시
     * null
     */
    @PostMapping("")
    fun findUserEmail(@RequestParam userName: String, @RequestParam userPhoneNum: String): BaseResponse<String?> {
        return BaseResponse(userService.findUserEmail(userName,userPhoneNum));
    }


    /**
     * User logout
     *
     * @param session
     *
     * 세션 전체 지워서 로그아웃
     *
     */

    @DeleteMapping("")
    fun userLogout(session: HttpSession): BaseResponse<String> {
        session.invalidate()
        return BaseResponse("true")
    }
    /**
     * 비밀번호 재설정
     *
     * @param userName
     * @param userEmail
     * @return
     * 해당 유저 이메일을 가지는 계정의 이름과 일치하면 비밀번호 리턴
     */

    @PostMapping("")
    fun resetPassword(@RequestParam userName:String, @RequestParam userEmail: String, @RequestParam userPassword: String): BaseResponse<String> {
        val user: User ?= userService.validateUserEmailAndName(userName, userEmail)
        return if(user != null) {
            userService.setUserPassword(user,userPassword)
            BaseResponse("true")
        } else {
            BaseResponse("false")
        }

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
     * 0 -> password 틀림
     * -1 -> 이메일 존재 X
     * else -> token 반환
     */

    @PostMapping("/login")
    fun login(@RequestParam userEmail: String, @RequestParam password: String, session: HttpSession) : BaseResponse<String>{
        val res: String = userService.userLogin(userEmail,password)

        if(res != "0"&& res != "-1") {
            val user: User? = userService.findUser(userEmail)
            if (user != null) {
                session.setAttribute("userId",user.userId)
            }
        }
        return BaseResponse(res)
    }

    /**
     * Id check 이메일 중복 체크
     *
     * @param userEmail
     */
    @PostMapping("/idcheck")
    fun idCheck(@RequestParam userEmail: String) = BaseResponse(userService.idCheck(userEmail))

}