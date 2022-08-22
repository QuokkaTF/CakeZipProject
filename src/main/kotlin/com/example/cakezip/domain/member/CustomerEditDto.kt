package com.example.cakezip.domain.member

class CustomerEditDto(
    var userEmail: String,
    var password: String,
    var phoneNum: String,
    var userName: String,
    var gender: Gender,
    var areas: String
) {
    fun toUser() = User(userEmail,password,phoneNum,userName,UserType.CUSTOMER)
    fun toCustomer(user: User) = Customer(user,gender,areas)
}