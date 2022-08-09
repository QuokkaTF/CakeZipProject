package com.example.cakezip.service

import com.example.cakezip.cake.Cake
import com.example.cakezip.cake.CakeOptionList
import com.example.cakezip.cake.CakeTask
import com.example.cakezip.repository.CakeRepository
import com.example.cakezip.repository.CakeTaskRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class CakeTaskService(
    private val cakeTaskRepository: CakeTaskRepository,
    ) {

    fun findOptionDESIGN(id:Long): String? {
        var result : String = "없음"
        if(cakeTaskRepository.findOptionDESIGN(id)!=null){
            result= cakeTaskRepository.findOptionDESIGN(id).toString()
        }
            return result
    }

    fun findOptionSIZE(id:Long): String? {
        var result : String = "없음"
        if(cakeTaskRepository.findOptionSIZE(id)!=null){
            result= cakeTaskRepository.findOptionSIZE(id).toString()
        }
        return result
    }

    fun findOptionSFLAVOR(id:Long): String? {
        var result : String = "없음"
        if(cakeTaskRepository.findOptionSFLAVOR(id)!=null){
            result= cakeTaskRepository.findOptionSFLAVOR(id).toString()
        }
        return result
    }

    fun findOptionCFLAVOR(id:Long): String? {
        var result : String = "없음"
        if(cakeTaskRepository.findOptionCFLAVOR(id)!=null){
            result= cakeTaskRepository.findOptionCFLAVOR(id).toString()
        }
        return result
    }

    fun findOptionCCOLOR(id:Long): String? {
        var result : String = "없음"
        if(cakeTaskRepository.findOptionCCOLOR(id)!=null){
            result= cakeTaskRepository.findOptionCCOLOR(id).toString()
        }
        return result
    }

    fun findOptionLCOLOR(id:Long): String? {
        var result : String = "없음"
        if(cakeTaskRepository.findOptionLCOLOR(id)!=null){
            result= cakeTaskRepository.findOptionLCOLOR(id).toString()
        }
        return result
    }

}