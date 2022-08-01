package com.example.cakezip.domain

import lombok.Getter
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@Getter
class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private val createdAt: LocalDateTime? = null

    @LastModifiedDate
    private val updatedAt: LocalDateTime? = null
}