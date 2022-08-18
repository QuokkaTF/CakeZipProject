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
        val customer = customerRepository.findByCustomerId(customerId)
        val shop = shopRepository.findByShopId(shopId)

        //중복 좋아요 방지
        if (isLike(customer, shop)) {
            // 좋아요 해놨으면 다시 삭제
            //println("외않되???")
            likeListRepository.deleteByCustomerAndShop(customer, shop)
            return false

        }
        //좋아요 안해놨으면 새로 만들기
        val newLike = LikeList(
            shop = shop,
            customer = customer,
        )
        likeListRepository.save(newLike)

        return true

    }

    fun getLikeCount(shopId: Long): Int? {
        val shop = shopRepository.findByShopId(shopId)
        return likeListRepository.countByShop(shop)
    }


    //사용자 -> 라이크 테이블에서 사용자 키로 가게 찾기
    fun getLikedShops(customerId: Long): List<Shop>? {
        val customer = customerRepository.findByCustomerId(customerId)
        val shopList: ArrayList<Shop> = ArrayList()
        //val likedShop = likeListRepository.findByCustomer(customer)

        for (liked in likeListRepository.findByCustomer(customer)) {

            // TODO: 가게 표시 코드 합치기
        }

        return shopList
    }

}

