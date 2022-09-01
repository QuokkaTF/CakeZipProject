package com.example.cakezip.controller

import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.member.User
import com.example.cakezip.domain.member.UserType
import com.example.cakezip.dto.Message
import com.example.cakezip.dto.ShopSimpleInfoDto
import com.example.cakezip.repository.ShopRepository
import com.example.cakezip.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class LikeController(
    private val likeListService: LikeListService,
    private val shopRepository: ShopRepository,
) {
    val noAccessMessage: Message = Message("접근할 수 없는 페이지입니다.", "/")

    @PostMapping("/like/{shopId}")
    @ResponseBody
    fun shopLike(model: Model, @PathVariable("shopId") shopId: Long, session: HttpSession): Boolean {
        val shop = shopRepository.findByShopId(shopId)
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer = session.getAttribute("customer") as Customer
            model.addAttribute("data", Message("", ""))
            val res = likeListService.addLike(customer, shop)
            session.setAttribute("likeCount",likeListService.getCustomerLikeCount(customer))
            println(res)
            println("--------")
            return res
        } else {
            model.addAttribute("data", noAccessMessage)
        }
        return false
    }

    @GetMapping("/likedshop")
    fun likedShopList(model: Model, session: HttpSession, @RequestParam(value = "nowPage", defaultValue = "0") nowPage: Int): String {
        val user: User = session.getAttribute("user") as User
        if (user.userType == UserType.CUSTOMER) {
            val customer = session.getAttribute("customer") as Customer
            if (likeListService.getLikedShopList(customer).isEmpty()) {
                model.addAttribute("data", Message("좋아요한 가게가 아직 존재하지 않습니다.", "/"))
            } else {
                val row = 4
                val list: ArrayList<ShopSimpleInfoDto> = ArrayList()
                val temp = likeListService.getLikedShopList(customer)
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
                model.addAttribute("shops", list)
                model.addAttribute("data", Message("", ""))
            }
        } else {
            model.addAttribute("data", noAccessMessage)
        }

        return "likedShop"
    }
}
