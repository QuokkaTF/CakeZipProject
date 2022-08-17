package com.example.cakezip.controller

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.OptionTitleType.*
import com.example.cakezip.repository.CakeOptionListRepository
import com.example.cakezip.service.OptionDetailService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import java.util.Optional

@Controller
class OptionController (private val optionDetailService: OptionDetailService, private val optionListRepository: CakeOptionListRepository){

    @GetMapping("/options/{type}/{shopId}")
    fun getShopDesignOption(@PathVariable("type") type:String,@PathVariable("shopId") shopId:Long, model: Model) : String{
        var optionDetailList : List<CakeOptionList> = ArrayList()
        when(type) {
            "design" -> optionDetailList = optionDetailService.getOptionDetailByShopAndType(shopId, DESIGN)
            "size" -> optionDetailList = optionDetailService.getOptionDetailByShopAndType(shopId, SIZE)
            "sheet" -> optionDetailList = optionDetailService.getOptionDetailByShopAndType(shopId, SFLAVOR)
            "cream" -> optionDetailList = optionDetailService.getOptionDetailByShopAndType(shopId, CFLAVOR)
            "creamcolor" -> optionDetailList = optionDetailService.getOptionDetailByShopAndType(shopId, CCOLOR)
            "letter" -> optionDetailList = optionDetailService.getOptionDetailByShopAndType(shopId, LCOLOR)
        }
        model.addAttribute("type", type)
        model.addAttribute("optionDetail",optionDetailList)
        return "optionpage"
    }

    @DeleteMapping("/options/delete/detail/{optionId}")
    fun deleteOption(@PathVariable("optionId") optionId:Long) {
        var op = optionListRepository.findByCakeOptionListId(optionId)
        optionListRepository.delete(op.get())
    }

    @GetMapping("/options/modify/detail/{optionId}")
    fun modifyOption(@PathVariable("optionId") optionId:Long, model:Model) :String {
        var cakeOption = optionListRepository.findByCakeOptionListId(optionId)
        model.addAttribute("cakeOption", cakeOption.get())
        return "editOption"
    }

    @PutMapping("/options/modify/detail/{optionId}")
    fun modifyOption(@PathVariable("optionId") optionId:Long, optionDetail: String, optionPrice:Long) :String {
        var oldOption: Optional<CakeOptionList> = optionListRepository.findByCakeOptionListId(optionId)
        oldOption.get().optionDetail = optionDetail
        oldOption.get().optionPrice = optionPrice
        optionListRepository.save(oldOption.get())

        return "index"
    }
}