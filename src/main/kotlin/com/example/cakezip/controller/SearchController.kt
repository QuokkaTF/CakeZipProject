package com.example.cakezip.controller

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.Seller
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.dto.ShopDetailInfoDto
import com.example.cakezip.dto.ShopSimpleInfoDto
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class SearchController(
    private val customerService: CustomerService,
    private val shopService: ShopService,
) {
    @GetMapping("/search")
    fun getSearchList(model: Model, @RequestParam(value = "keyword") keyword: String, @RequestParam(value = "nowPage", defaultValue = "0") nowPage: Int): String {
        val row = 4
        val list: ArrayList<ShopDetailInfoDto> = ArrayList()
        val temp = shopService.searchShop2(keyword)
        var totalPage = temp.size.div(row)

        if((temp.size % row) > 0) {
            totalPage += 1
        }

        for (i in nowPage * row until (nowPage * row) + row) {
            if(i >= temp.size) {
                break
            }
            list.add(temp[i])
        }
        model.addAttribute("nowPage", nowPage)
        model.addAttribute("totalPage", totalPage)
        model.addAttribute("searchList", list)
        model.addAttribute("keyword", keyword)

        return "search"
    }
}
