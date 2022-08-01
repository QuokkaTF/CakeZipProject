package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import lombok.*
import javax.persistence.*

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Review : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private val reviewId: Long? = null
    
    @Column(name = "review_title")
    private val reviewTitle: String? = null

    @Column(name = "review_content")
    private val reviewContent: String? = null

    @Column(name = "review_score")
    private val reviewScore: String? = null

    private val status: String?=null


    @OneToOne
    @JoinColumn(name = "cake_id")
    private val cake: Cake?= null

}
