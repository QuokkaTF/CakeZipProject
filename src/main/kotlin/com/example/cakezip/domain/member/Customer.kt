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
class Customer : BaseEntity(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    @NotNull
    private val customerId: Long = 0

    @OneToOne
    @JoinColumn(name = "user_id")
    private val user:User ?= null

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private val gender: Gender? = null

    @Column(name="area")
    private val ares : String?=null

    private val status : String?=null
}