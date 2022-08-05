package com.example.cakezip.domain

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@NoArgsConstructor
open class BaseEntity {

    var status: String?= null

    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime?=null

    @LastModifiedDate
    @Column(updatable = false)
    var updatedAt: LocalDateTime?=null
}