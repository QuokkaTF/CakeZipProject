package com.example.cakezip.controller

import com.example.cakezip.config.BaseResponse
import com.example.cakezip.domain.member.UserDto
import com.example.cakezip.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/users")
class UserController(
    private val userService : UserService) {

    @PostMapping("")
    fun signIn(@RequestBody userDto: UserDto) = BaseResponse(userService.createUser(userDto))

    @PostMapping("/login")
    fun login(@RequestParam userEmail: String, @RequestParam password: String ) = BaseResponse(userService.userLogin(userEmail,password))

    @PostMapping("/idcheck")
    fun idCheck(@RequestParam userEmail: String) = BaseResponse(userService.idCheck(userEmail))

}