package com.example.cakezip.repository

import com.example.cakezip.domain.Cake
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

interface CartRepository : JpaRepository<Cake, Long> {

//    override fun findAll(): List<Cake>
}