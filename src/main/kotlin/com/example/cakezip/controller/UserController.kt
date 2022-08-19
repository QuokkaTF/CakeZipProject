package com.example.cakezip.controller

import com.example.cakezip.config.BaseResponse
import com.example.cakezip.domain.member.*
import com.example.cakezip.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@RequestMapping("/users")
@Controller
class UserController(
    private val userService : UserService) {

    @GetMapping("/login")
    fun getUserLoginView() = "login"

    @GetMapping("/register")
    fun getUserRegisterView() = "user-register"

    @GetMapping("/customers/register")
    fun getCustomerRegisterView() = "customer-register"

    @GetMapping("/sellers/register")
    fun getSellerRegisterView() = "seller-register"

    @GetMapping("/password")
    fun getFindPasswordView() = "find-password"

    @GetMapping("/email")
    fun getFindUserEmailView() = "find-email"

    @GetMapping("/edit")
    fun getUserEditView(session: HttpSession,model: Model): String {
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.SELLER) {
            println("this is seller")
            val seller: Seller = userService.findSellerByUser(user)
            model.addAttribute("sellerDto",seller.toSellerDto())
        }
        return "seller-edit"
    }

    @PutMapping("/customer/edit")
    fun editSeller(session: HttpSession, customerDto: CustomerDto): String {
        return "temp"
    }

    @PutMapping("/seller/edit")
    fun editCustomer(session: HttpSession, sellerDto: SellerDto): String {
        return "temp"
    }


    @DeleteMapping("/deactivate") // 탈퇴
    fun deactivateUser(session: HttpSession): String {
        userService.secessionUser(session.getAttribute("user") as User)
        session.invalidate()
        return "redirect:/home"
    }

    @PostMapping("/email")
    fun findUserEmail(@RequestParam userName: String, @RequestParam userPhoneNum: String, model: Model): String {
        val userEmail: String? = userService.findUserEmail(userName,userPhoneNum)
        if(userEmail==null) {
            model.addAttribute("error","일치하는 회원이 없습니다.")
        }else {
            model.addAttribute("error",userEmail)
        }
        return "find-email"

    }

    @GetMapping("/logout")
    fun userLogout(session: HttpSession): String {
        session.invalidate()
        return "redirect:/home"
    }

    @PostMapping("/password")
    fun resetPassword(@RequestParam userName:String, @RequestParam userEmail: String, @RequestParam userPassword: String, model: Model): String {
        val user: User ?= userService.validateUserEmailAndName(userName, userEmail)
        if(user != null) {
            userService.setUserPassword(user,userPassword)
            return "redirect:/home"
        } else {
            model.addAttribute("error","해당하는 계정이 없습니다.")
            return "find-password"
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
    fun login(@RequestParam userEmail: String, @RequestParam password: String, session: HttpSession, model: Model) : String{
        val res: String = userService.userLogin(userEmail,password)

        if(res != "0"&& res != "-1") {
            val user: User? = userService.findUser(userEmail)
            if (user != null) {
                //session.setAttribute("userId",user.userId)
                session.setAttribute("user",user)

            }
        } else {
            model.addAttribute("error","비밀번호가 틀렸거나 존재하지않는 이메일입니다.")
            return "login"
        }

        return "redirect:/home"
    }

    @GetMapping("/idCheck")
    @ResponseBody
    fun idCheck(userEmail: String): ResponseEntity<Boolean>{
        println(userEmail)
        return ResponseEntity.ok(userService.existsUser(userEmail))
    }

    @GetMapping("/mypage")
    fun getMyPage(): String {
        println("mypage")
        return "mypage"
    }
}