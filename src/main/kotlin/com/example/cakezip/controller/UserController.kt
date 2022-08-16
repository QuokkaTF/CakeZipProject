package com.example.cakezip.controller

import com.example.cakezip.config.BaseResponse
import com.example.cakezip.domain.member.CustomerDto
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserDto
import com.example.cakezip.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@RequestMapping("/users")
@Controller
class UserController(
    private val userService : UserService) {

    @GetMapping("/register")
    fun getUserRegisterView() = "user-register"

    @GetMapping("/customers/register")
    fun getCustomerRegisterView() = "customer-register"

    @GetMapping("/sellers/register")
    fun getSellerRegisterView() = "seller-register"

    @GetMapping("/editInfo")
    fun getUserEditView() = "editInfo"

    @DeleteMapping("/deactivate") // 탈퇴
    fun deactivateUser(session: HttpSession): String {
        userService.secessionUser(session.getAttribute("user") as User)
        session.invalidate()
        return "redirect:/home"
    }

    @PostMapping("/email")
    fun findUserEmail(@RequestParam userName: String, @RequestParam userPhoneNum: String): BaseResponse<String?> {
        return BaseResponse(userService.findUserEmail(userName,userPhoneNum));
    }

    @GetMapping("/logout")
    fun userLogout(session: HttpSession): String {
        session.invalidate()
        return "redirect:/home"
    }

    @PostMapping("/password")
    fun resetPassword(@RequestParam userName:String, @RequestParam userEmail: String, @RequestParam userPassword: String): BaseResponse<String> {
        val user: User ?= userService.validateUserEmailAndName(userName, userEmail)
        return if(user != null) {
            userService.setUserPassword(user,userPassword)
            BaseResponse("true")
        } else {
            BaseResponse("false")
        }

    }

    @PostMapping("/customers")
    fun customersRegister(userDto: UserDto, customerDto: CustomerDto): String {
        userService.createCustomer(userDto,customerDto)
        return "redirect:/home"
    }

    @PostMapping("/sellers")
    fun sellersRegister(userDto: UserDto): String {
        userService.createSeller(userDto)

        // TODO 가게 등록 페이지로 리다이렉트하도록 수정해야함

        return "redirect:/home"
    }

    @PostMapping("/login")
    fun login(@RequestParam userEmail: String, @RequestParam password: String, session: HttpSession) : String{
        val res: String = userService.userLogin(userEmail,password)

        if(res != "0"&& res != "-1") {
            val user: User? = userService.findUser(userEmail)
            if (user != null) {
                //session.setAttribute("userId",user.userId)
                session.setAttribute("user",user)
            }
        }
        return "redirect:/home"
    }

    @PostMapping("/idCheck")
    fun idCheck(@RequestParam userEmail: String) = BaseResponse(userService.existsUser(userEmail))

    @GetMapping("/mypage")
    fun getMyPage(): String {
        println("mypage")
        return "mypage"
    }
}