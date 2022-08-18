package com.example.cakezip.service


import com.example.cakezip.domain.LikeList
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.repository.*
import org.springframework.stereotype.Service


@Service
class LikeListService(
    private val reviewRepository: ReviewRepository,
    private val customerRepository: CustomerRepository,
    private val shopRepository: ShopRepository,
    private val cakeRepository: CakeRepository,
    private val likeListRepository: LikeListRepository,
) {

    //fun insertLike(CustomerId: Long, ShopId: Long)

    fun isLike(customer: Customer, shop: Shop): Boolean {
        return likeListRepository.findByCustomerAndShop(customer, shop) != null // 있으면 True 없으면 False
    }

    fun addLike(customerId: Long, shopId: Long): Boolean {
        val c = customerRepository.findByCustomerId(customerId)
        val s = shopRepository.findByShopId(shopId)

        //중복 좋아요 방지
        if (isLike(c, s)) {
            // 좋아요 해놨으면 다시 삭제
            println("왜 안돼???")
            likeListRepository.deleteByCustomerAndShop(c, s)
//
//            if (oldLike?.status == "active") { // 좋아요 해놨으면 취소
//                oldLike.status = "inactive"
//                likeListRepository.save(oldLike)
//                return 0
//            }
//            else {
//                if (oldLike != null) {
//                    oldLike.status = "active"
//                    likeListRepository.save(oldLike)
//                    return 1
//                }
//            }
            return false

        }
        //좋아요 안해놨으면 새로 만들기
        val newLike = LikeList(
            shop = s,
            customer = c,
        )
        likeListRepository.save(newLike)

        return true

    }

    fun getLikeCount(shopId: Long): Int? {
        val shop = shopRepository.findByShopId(shopId)
        //val likedList : List<LikeList>? = likeListRepository.findByShop(shop) // 전체 샵 가져옴
        // status= active 필터링 필요
//        for ( in likeListRepository.findByShop(shop)) {
//
//        }

        //return likeListRepository.countByShopAndStatus(shop, "active") ?: 0 // null 값이면 0 반환
        return likeListRepository.countByShop(shop)
    }

}

