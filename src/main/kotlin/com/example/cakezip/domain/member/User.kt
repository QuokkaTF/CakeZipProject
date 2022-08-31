package com.example.cakezip.domain.member

import com.example.cakezip.domain.BaseEntity
import org.jetbrains.annotations.NotNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import javax.persistence.*

@Entity
class User (

    @NotNull
    var userEmail: String,

    @NotNull
    @Column(name="password")
    var pass: String,

    @NotNull
    var phoneNum: String,

    @NotNull
    var userName: String,

    @NotNull
    @Enumerated(value = EnumType.STRING)
    var userType: UserType

    ) : BaseEntity(), Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long?=null

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = ArrayList<GrantedAuthority>()
        if(userType == UserType.SELLER)
            authorities.add(SimpleGrantedAuthority("ROLE_SELLER"))
        else
            authorities.add(SimpleGrantedAuthority("ROLE_CUSTOMER"))
        return authorities
    }

    override fun getPassword() = pass

    override fun getUsername() = userEmail

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
    fun setByCustomerEditDto(customerEditDto: CustomerEditDto) {
        userEmail = customerEditDto.userEmail
        pass = customerEditDto.password
        phoneNum = customerEditDto.phoneNum
        userName = customerEditDto.userName
    }

    fun setBySellerEditDto(sellerEditDto: SellerDto) {
        userEmail = sellerEditDto.userEmail
        pass = sellerEditDto.password
        userName = sellerEditDto.userName
        phoneNum = sellerEditDto.userPhoneNum
        userType = UserType.SELLER


    }



    }
