package com.example.cakezip.domain.member

import com.example.cakezip.domain.BaseEntity
import javax.persistence.*

@Entity
class Customer(
    @OneToOne
    @JoinColumn(name = "user_id")
    val user:User,

    @Enumerated(value = EnumType.STRING)
    val gender: Gender,

    var areas : String,
) : BaseEntity(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val customerId: Long=0

    fun toCustomerEditDto() = CustomerEditDto(user.userEmail,user.password,user.phoneNum,user.userName,gender,areas)
}