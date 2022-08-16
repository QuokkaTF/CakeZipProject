package com.example.cakezip.domain.member

data class UserDto (
    var userEmail : String,
    var password : String,
    var phoneNum : String,
    var userName : String,
        ){



    fun toEntity(userType: UserType) : User {
        return User(userEmail,password,phoneNum,userName,userType)
    }
}