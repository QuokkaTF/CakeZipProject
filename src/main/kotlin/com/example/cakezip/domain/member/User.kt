package com.example.cakezip.domain.member

import com.example.cakezip.domain.BaseEntity
import lombok.*
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class User : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @NotNull
    private val userId: Long = 0

    @Column(name = "user_email")
    private val userEmail: String? = null

    @Column(name = "user_password")
    private val password: String?= null

    @Column(name = "user_phone")
    private val phoneNum: String?= null

    @Column(name = "user_type")
    @Enumerated(value = EnumType.STRING)
    private val userType: UserType? = null

    private val status: String?=null

}
