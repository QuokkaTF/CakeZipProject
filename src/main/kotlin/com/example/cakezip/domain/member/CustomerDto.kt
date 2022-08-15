package com.example.cakezip.domain.member

class CustomerDto(
    var gender: Gender,
    var areas: String,
) {


    fun toEntity(user: User) : Customer {
        return Customer(user, gender,areas)
    }
}