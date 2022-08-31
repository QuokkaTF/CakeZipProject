package com.example.cakezip.controller

import com.example.cakezip.config.BaseResponse
import com.example.cakezip.domain.member.*
import com.example.cakezip.dto.Message
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
            val seller: Seller = userService.findSellerByUser(user)
            model.addAttribute("sellerDto",seller.toSellerDto())

            return "seller-edit"
        } else {
            val customer: Customer = userService.findCustomerByUser(user)
            model.addAttribute("customerEditDto",customer.toCustomerEditDto())

            return "customer-edit"
        }
    }

    @PostMapping("/customer/edit")
    fun editCustomer(session: HttpSession, customerEditDto: CustomerEditDto): String {
        userService.editCustomer(session.getAttribute("user") as User,customerEditDto)

        return "redirect:/home"
    }

    @PostMapping("/seller/edit")
    fun editSeller(session: HttpSession, sellerDto: SellerDto): String {
        userService.editSeller(session.getAttribute("user") as User,sellerDto)

        return "redirect:/home"
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
        return "redirect:/users/login"
    }

    @PostMapping("/login")
    fun login(@RequestParam userEmail: String, @RequestParam password: String, session: HttpSession, model: Model) : String{
        val res: String = userService.userLogin(userEmail,password)

        if(res != "0"&& res != "-1") {
            val user: User? = userService.findUser(userEmail)
            if (user != null) {
                //session.setAttribute("userId",user.userId)
                session.setAttribute("user",user)
                if(user.userType == UserType.CUSTOMER) {
                    session.setAttribute("customer", userService.findCustomerByUser(user))
                } else {
                    session.setAttribute("seller", userService.findSellerByUser(user))
                    return "redirect:/sellers/myshop"
                }

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
        return ResponseEntity.ok(userService.existsUser(userEmail))
    }
}