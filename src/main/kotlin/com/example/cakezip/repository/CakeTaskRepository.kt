package com.example.cakezip.repository

import com.example.cakezip.cake.Cake
import com.example.cakezip.cake.CakeOptionList
import com.example.cakezip.cake.CakeTask
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CakeTaskRepository : JpaRepository<CakeTask, Long> {

    @Query("select cakeOptionList.optionDetail from CakeTask caketask" +
            " where caketask.cake.cakeId=:id and caketask.cakeOptionList.optionTitle='DESIGN'")
    fun findOptionDESIGN(id:Long): String?

    @Query("select cakeOptionList.optionDetail from CakeTask caketask" +
            " where caketask.cake.cakeId=:id and caketask.cakeOptionList.optionTitle='SIZE'")
    fun findOptionSIZE(id:Long): String?

    @Query("select cakeOptionList.optionDetail from CakeTask caketask" +
            " where caketask.cake.cakeId=:id and caketask.cakeOptionList.optionTitle='SFLAVOR'")
    fun findOptionSFLAVOR(id:Long): String?

    @Query("select cakeOptionList.optionDetail from CakeTask caketask" +
            " where caketask.cake.cakeId=:id and caketask.cakeOptionList.optionTitle='CFLAVOR'")
    fun findOptionCFLAVOR(id:Long): String?

    @Query("select cakeOptionList.optionDetail from CakeTask caketask" +
            " where caketask.cake.cakeId=:id and caketask.cakeOptionList.optionTitle='CCOLOR'")
    fun findOptionCCOLOR(id:Long): String?

    @Query("select cakeOptionList.optionDetail from CakeTask caketask" +
            " where caketask.cake.cakeId=:id and caketask.cakeOptionList.optionTitle='LCOLOR'")
    fun findOptionLCOLOR(id:Long): String?

}