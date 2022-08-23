package com.example.cakezip.controller

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.OptionTitleType.*
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.dto.EditOptionDto
import com.example.cakezip.dto.Message
import com.example.cakezip.dto.NewOptionReqDto
import com.example.cakezip.repository.CakeOptionListRepository
import com.example.cakezip.service.OptionDetailService
import com.example.cakezip.service.ShopService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.Optional
import javax.servlet.http.HttpSession

@Controller
class OptionController (
    private val optionDetailService: OptionDetailService,
    private val shopService: ShopService
    ){
    val noAccessMessage: Message = Message("접근할 수 없는 페이지입니다.", "/")

    @GetMapping("/sellers/myshop/options/show/{type}")
    fun getShopDesignOption(@PathVariable("type") type:String,session: HttpSession, model: Model) : String{
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.SELLER) {
            val seller: Seller = session.getAttribute("seller") as Seller
            var shop : Shop? = shopService.getMyShop(seller)
            val shopId : Long = shop!!.shopId!!

            var optionDetailList : List<CakeOptionList> = ArrayList()

            var typeInput = type
            when(type) {
                "DESIGN" -> typeInput = "design"
                "SIZE" -> typeInput = "size"
                "SFLAVOR" -> typeInput = "sheet"
                "CFLAVOR" -> typeInput = "cream"
                "CCOLOR" -> typeInput = "creamcolor"
                "LCOLOR" -> typeInput = "letter"
            }

            when(typeInput) {
                "design" -> optionDetailList = optionDetailService.getOptionDetailByShopAndTypeAndStatus(shopId, DESIGN, "active")
                "size" -> optionDetailList = optionDetailService.getOptionDetailByShopAndTypeAndStatus(shopId, SIZE, "active")
                "sheet" -> optionDetailList = optionDetailService.getOptionDetailByShopAndTypeAndStatus(shopId, SFLAVOR, "active")
                "cream" -> optionDetailList = optionDetailService.getOptionDetailByShopAndTypeAndStatus(shopId, CFLAVOR, "active")
                "creamcolor" -> optionDetailList = optionDetailService.getOptionDetailByShopAndTypeAndStatus(shopId, CCOLOR, "active")
                "letter" -> optionDetailList = optionDetailService.getOptionDetailByShopAndTypeAndStatus(shopId, LCOLOR, "active")
            }
            model.addAttribute("shopId", shopId)
            model.addAttribute("type", typeInput)
            model.addAttribute("optionDetail",optionDetailList)
            model.addAttribute("data",Message("","/"))
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return "optionpage"
    }

    @GetMapping("/sellers/myshop/options/new/{type}")
    fun addOptionPage(@PathVariable("type") type:String ,model: Model, session: HttpSession) : String {
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.SELLER) {
            val seller: Seller = session.getAttribute("seller") as Seller
            var shop : Shop? = shopService.getMyShop(seller)
            val shopId : Long = shop!!.shopId!!
            model.addAttribute("shopId", shopId)
            model.addAttribute("type", type)
            model.addAttribute("form", NewOptionReqDto())
            model.addAttribute("data",Message("","/"))
        }else {
            model.addAttribute("data", noAccessMessage)
        }
        return "addOption"
    }

    @RequestMapping(value = arrayOf("/sellers/myshop/options/new"), method = arrayOf(RequestMethod.POST))
    fun addOption(newOptionReqDto: NewOptionReqDto) : String {
        optionDetailService.addNewOption(newOptionReqDto)
        return "redirect:/sellers/myshop/options/show/"+newOptionReqDto.optionType
    }

    @GetMapping("/sellers/myshop/options/{optionId}")
    fun modifyOption(@PathVariable("optionId") optionId:Long, model:Model, session: HttpSession) :String {
        val user: User = session.getAttribute("user") as User
        if(user.userType == UserType.SELLER) {
            var cakeOption = optionDetailService.findByCakeOptionListId(optionId)
            model.addAttribute("cakeOption", cakeOption)
            model.addAttribute("form", EditOptionDto())
            model.addAttribute("data",Message("","/"))
        }else {
            model.addAttribute("data",noAccessMessage)
        }
        return "editOption"
    }

    @PutMapping("/sellers/myshop/options/{optionId}")
    fun modifyOption(@PathVariable("optionId") optionId:Long, editOptionDto: EditOptionDto) :String {
        var editOption = optionDetailService.editCakeOption(optionId, editOptionDto)
        var type = ""
        when(editOption.optionTitle) {
            DESIGN -> type = "design"
            SIZE -> type = "size"
            SFLAVOR -> type = "sheet"
            CFLAVOR -> type = "cream"
            CCOLOR -> type = "creamcolor"
            LCOLOR -> type = "letter"
        }
        return "redirect:/sellers/myshop/options/show/$type"
    }


    @DeleteMapping("/sellers/myshop/options/{optionId}")
    fun deleteOption(@PathVariable("optionId") optionId:Long) :String{
        var deleteOption = optionDetailService.deleteCakeOption(optionId)
        var type = ""
        when(deleteOption.optionTitle) {
            DESIGN -> type = "design"
            SIZE -> type = "size"
            SFLAVOR -> type = "sheet"
            CFLAVOR -> type = "cream"
            CCOLOR -> type = "creamcolor"
            LCOLOR -> type = "letter"
        }
        return "redirect:/sellers/myshop/options/show/$type"
    }


}