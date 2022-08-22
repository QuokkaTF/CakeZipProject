package com.example.cakezip.service

import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeOptionList
import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.domain.shop.Shop
import com.example.cakezip.repository.CakeOptionListRepository
import com.example.cakezip.repository.CakeRepository
import com.example.cakezip.repository.CakeTaskRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class CakeService(
    private val cakeRepository: CakeRepository,
    private val cakeTaskRepository: CakeTaskRepository,
    private val cakeOptionListRepository: CakeOptionListRepository,
    private val shopImgService: ShopImgService,
) {
    fun findByCakeId(id: Long): Cake = cakeRepository.findByCakeId(id)

    fun findByCustomerAndCakeStatus(customer: Customer, cakeStatus: CakeStatusType): List<Cake> =
        cakeRepository.findByCustomerAndCakeStatus(customer, cakeStatus)

    fun findByCustomerAndCakeStatusNot(customer:Customer, cakeStatus:CakeStatusType): List<Cake> =
        cakeRepository.findByCustomerAndCakeStatusNot(customer, cakeStatus)


    fun getSellerCakeList(shop: Shop, cakeStatus: CakeStatusType): List<Cake> =
        cakeRepository.findByShopAndCakeStatusNot(shop, cakeStatus)


    @Transactional
    fun deleteAllByCakeId(id: Long) = cakeRepository.deleteAllByCakeId(id)

    @Transactional
    fun deleteAllByCustomerAndCakeStatus(customer: Customer, cakeStatus: CakeStatusType) =
        cakeRepository.deleteAllByCustomerAndCakeStatus(customer, cakeStatus)


    @Transactional
    fun updateCakeStatus(CakeId: Long, statusCheck: CakeStatusType) {
        val cake = cakeRepository.findByCakeId(CakeId)

        if (cake.cakeStatus.equals(statusCheck)) {
            //TODO: 이미 주문 취소일 경우 예외 처리
        } else {
            cake.cakeStatus = statusCheck
            cakeRepository.save(cake)
        }
    }

    fun addCartCake(
        pickupDate: String, letterText: String, etc: String, totalPrice: Int, cakeStatus: CakeStatusType,
        shop: Shop, customer: Customer
    ): Cake {
        val cake = Cake(
            pickupDate = pickupDate,
            letterText = letterText,
            etc = etc,
            totalPrice = totalPrice,
            cakeStatus = cakeStatus,
            shop = shop,
            customer = customer,
        )
        return cakeRepository.save(cake)
    }

    fun sumPrice(cake:Cake):Int{
        var totalPrice : Int=0
        for (ct in cakeTaskRepository.findByCake(cake)) {
            if (ct.cakeOptionList.cakeOptionListId != null) {
                var cakeOptionList: Optional<CakeOptionList> =
                    cakeOptionListRepository.findByCakeOptionListId(ct.cakeOptionList.cakeOptionListId!!)
                totalPrice += cakeOptionList.get().optionPrice
            }
        }
        cake.totalPrice = totalPrice
        cakeRepository.save(cake)
        return totalPrice
    }

    fun getCakeOptionList(cake:Cake):HashMap<String, Any>{
        var cake_hashMap = HashMap<String, Any>()
        for (ct in cakeTaskRepository.findByCake(cake)) {
            if (ct.cakeOptionList.cakeOptionListId != null) {
                var cakeOptionList: Optional<CakeOptionList> =
                    cakeOptionListRepository.findByCakeOptionListId(ct.cakeOptionList.cakeOptionListId!!)
                cake_hashMap.put(cakeOptionList.get().optionTitle.toString(), cakeOptionList.get().optionDetail)
                cake_hashMap.put(
                    cakeOptionList.get().optionTitle.toString() + "price",
                    cakeOptionList.get().optionPrice
                )
            }
        }
        cake_hashMap.put("cake",cake)
        cake_hashMap.put("img", shopImgService.getThumbnail(cake.shop).shopImgUrl)
        return cake_hashMap
    }

    fun getCakeOptionListAll(cakes: List<Cake>):ArrayList<HashMap<String, Any>>{
        var cake_arrayList: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
        for (c in cakes){
            cake_arrayList.add(getCakeOptionList(c))
        }
        return cake_arrayList
    }
    fun countByCustomer(customer:Customer):Int = cakeRepository.countByCustomer(customer)
}
