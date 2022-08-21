package com.example.cakezip.controller

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.OptionTitleType.*
import com.example.cakezip.dto.EditOptionDto
import com.example.cakezip.dto.NewOptionReqDto
import com.example.cakezip.repository.CakeOptionListRepository
import com.example.cakezip.service.OptionDetailService
import com.example.cakezip.service.ShopService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.Optional

@Controller
class OptionController (private val optionDetailService: OptionDetailService, private val shopService: ShopService,private val optionListRepository: CakeOptionListRepository){

    @GetMapping("/sellers/myshop/options/{type}/{shopId}")
    fun getShopDesignOption(@PathVariable("type") type:String,@PathVariable("shopId") shopId:Long, model: Model) : String{
        var optionDetailList : List<CakeOptionList> = ArrayList()
        when(type) {
            "design" -> optionDetailList = optionDetailService.getOptionDetailByShopAndTypeAndStatus(shopId, DESIGN, "active")
            "size" -> optionDetailList = optionDetailService.getOptionDetailByShopAndTypeAndStatus(shopId, SIZE, "active")
            "sheet" -> optionDetailList = optionDetailService.getOptionDetailByShopAndTypeAndStatus(shopId, SFLAVOR, "active")
            "cream" -> optionDetailList = optionDetailService.getOptionDetailByShopAndTypeAndStatus(shopId, CFLAVOR, "active")
            "creamcolor" -> optionDetailList = optionDetailService.getOptionDetailByShopAndTypeAndStatus(shopId, CCOLOR, "active")
            "letter" -> optionDetailList = optionDetailService.getOptionDetailByShopAndTypeAndStatus(shopId, LCOLOR, "active")
        }
        model.addAttribute("shopId", shopId)
        model.addAttribute("type", type)
        model.addAttribute("optionDetail",optionDetailList)
        return "optionpage"
    }

    @GetMapping("/sellers/myshop/options/new/{type}/{shopId}")
    fun addOptionPage(@PathVariable("shopId") shopId: Long, @PathVariable("type") type:String ,model: Model) : String {
        model.addAttribute("shopId", shopId)
        model.addAttribute("type", type)
        model.addAttribute("form", NewOptionReqDto())
        return "addOption"
    }

    @RequestMapping(value = arrayOf("/sellers/myshop/options/new"), method = arrayOf(RequestMethod.POST))
    fun addOption(newOptionReqDto: NewOptionReqDto) : String {
        optionDetailService.addNewOption(newOptionReqDto)
        return "redirect:/sellers/myshop/options/"+newOptionReqDto.optionType+"/"+newOptionReqDto.shopId
    }

    @GetMapping("/sellers/myshop/options/{optionId}")
    fun modifyOption(@PathVariable("optionId") optionId:Long, model:Model) :String {
        var cakeOption = optionListRepository.findByCakeOptionListId(optionId)
        model.addAttribute("cakeOption", cakeOption.get())
        model.addAttribute("form", EditOptionDto())
        return "editOption"
    }

    @PutMapping("/sellers/myshop/options/{optionId}")
    fun modifyOption(@PathVariable("optionId") optionId:Long, editOptionDto: EditOptionDto) :String {
        println(editOptionDto)
        var oldOption: Optional<CakeOptionList> = optionListRepository.findByCakeOptionListId(optionId)
        oldOption.get().optionDetail = editOptionDto.optionDetail
        oldOption.get().optionPrice = editOptionDto.optionPrice
        optionListRepository.save(oldOption.get())
        return "redirect:/sellers/myshop/options/$optionId"
    }

    @DeleteMapping("/sellers/myshop/options/{optionId}")
    fun deleteOption(@PathVariable("optionId") optionId:Long) :String{
        var option = optionListRepository.findByCakeOptionListId(optionId)
        option.get().status="deactive"
        optionListRepository.save(option.get())
        return "index"
    }


}