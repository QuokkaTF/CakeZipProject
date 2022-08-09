package com.example.cakezip.controller

import com.example.cakezip.cake.CakeDto
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.service.CakeService
import com.example.cakezip.service.CakeTaskService
import org.apache.coyote.http11.Constants.a
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDateTime
import javax.persistence.PersistenceContext
import javax.swing.text.html.parser.Entity

@Controller
class CartController(
    private val cakeService: CakeService,
    private val cakeTaskService: CakeTaskService,
    ) {

    @GetMapping("/users/cart")
    fun getCartList(model: Model): String {
        model.addAttribute("cakeList", cakeService.findAll())


        var cakeOption =  ArrayList<CakeDto>();


        var cakeOptionDESIGN =  ArrayList<String>();
        var cakeOptionSIZE =  ArrayList<String>();
        var cakeOptionSFLAVOR =  ArrayList<String>();
        var cakeOptionCFLAVOR =  ArrayList<String>();
        var cakeOptionCCOLOR =  ArrayList<String>();
        var cakeOptionLCOLOR =  ArrayList<String>();
        for(i in cakeService.findId()){
            print("cakeOption~ LIST $i ")
            cakeOption.add(CakeDto(cakeService.findPickupDate(i).toString(),
                                    cakeService.findShop(i),
                                    cakeTaskService.findOptionDESIGN(i).toString(),
                                    cakeTaskService.findOptionSIZE(i).toString(),
                                    cakeTaskService.findOptionSFLAVOR(i).toString(),
                                    cakeTaskService.findOptionCFLAVOR(i).toString(),
                                    cakeTaskService.findOptionCCOLOR(i).toString(),
                                    cakeTaskService.findOptionLCOLOR(i).toString()))
        }

        model.addAttribute("cakeOption", cakeOption)

        model.addAttribute("cakeOptionDESIGN", cakeOptionDESIGN)
        model.addAttribute("cakeOptionSIZE", cakeOptionSIZE)
        model.addAttribute("cakeOptionSFLAVOR", cakeOptionSFLAVOR)
        model.addAttribute("cakeOptionCFLAVOR", cakeOptionCFLAVOR)
        model.addAttribute("cakeOptionCCOLOR", cakeOptionCCOLOR)
        model.addAttribute("cakeOptionLCOLOR", cakeOptionLCOLOR)

        return "cart" // product.html 반환
    }

}
