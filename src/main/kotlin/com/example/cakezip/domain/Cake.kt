package com.example.cakezip.domain

import com.example.cakezip.domain.BaseEntity
import lombok.*
import javax.persistence.*

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Cake : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cake_id")
    private val cakeId: Long? = null

    @Column(name = "pickup_date")
    private val pickupDate: String? = null

    @Column(name = "cake_status")
    private val cakeStatus: String? = null

    private val status: String?=null

}
