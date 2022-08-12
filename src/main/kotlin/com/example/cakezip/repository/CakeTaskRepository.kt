package com.example.cakezip.repository

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeTask
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CakeTaskRepository : JpaRepository<CakeTask, Long> {

    fun findByCake(cake:Cake) : List<CakeTask>

    fun deleteAllByCake_cakeId(id:Long)

    fun deleteAllByCake(cake:Cake)


}