package com.example.cakezip.service


import com.example.cakezip.domain.LikeList
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.repository.*
import org.springframework.stereotype.Service


@Service
class LikeListService(
    private val customerRepository: CustomerRepository,
    private val likeListRepository: LikeListRepository,
) {

    fun isLike(customer: Customer, shop: Shop): Boolean {
        return likeListRepository.findByCustomerAndShop(customer, shop) != null // 있으면 True 없으면 False
    }

    fun addLike(customer: Customer, shop: Shop): Boolean {
        if (isLike(customer, shop)) {
            likeListRepository.deleteByCustomerAndShop(customer, shop)
            return false

        }
        val newLike = LikeList(
            shop = shop,
            customer = customer,
        )
        likeListRepository.save(newLike)

        return true

    }

    fun getLikeCount(shop: Shop): Int {
        return likeListRepository.countByShop(shop)
    }

    fun getLikedShops(customerId: Long): List<Shop>? {
        val customer = customerRepository.findByCustomerId(customerId)
        val shopList: ArrayList<Shop> = ArrayList()

        for (liked in likeListRepository.findByCustomer(customer)) {
            // TODO: 가게 표시 코드 합치기
        }

        return shopList
    }

}


