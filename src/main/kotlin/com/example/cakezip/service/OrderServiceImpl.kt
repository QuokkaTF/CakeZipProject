package com.example.cakezip.service

import com.example.cakezip.domain.OrderDetail
import com.example.cakezip.domain.cake.Cake
import com.example.cakezip.domain.cake.CakeStatusType
import com.example.cakezip.domain.member.Customer
import com.example.cakezip.dto.OrderDto
import com.example.cakezip.repository.*
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl (
    private val orderRepository: OrderRepository,
    private val orderdetailRepository: OrderDetailRepository,
    private val cakeRepository: CakeRepository,
    private val shopRepository: ShopRepository,
    private val customerRepository: CustomerRepository,
) : OrderService {

    //// orderId 이용해서 orderdetail 테이블에서 cakeId 찾은 다음 그 cakeId 이용해서 띄우기!!!

//    override fun findCakesByOrderId(id: Long): List<Cake> {
//        return cakeRepository.findByOrderId(id)
//    }

//    override fun findOrdersByCustomerId(id: Long): List<Orders> {
//        val customerId = customerRepository.findCustomersByCustomerId()
//        return orderRepository.findOrdersByCustomer(id)
//    }
//
//    override fun findOrderDetailByOrderId(id: Long): OrderDetail {
//        return orderdetailRepository.findByOrderId(id)
//    }
//
//    override fun findOrderDetailByCustomerId(id: Long): List<OrderDetail> {
//        return orderdetailRepository.findByCustomerId(id)
//    }

    // 오더 아이디로 케이크 아이디 찾기 -> 케이크 아이디로 띄워주기
    //// customerId 받아와서 모든 오더 + 케이크 띄우기
    //
    override fun getCustomerAllOrders(CustomerId: Long): List<OrderDto>? {
        val customer: Customer = customerRepository.findByCustomerId(CustomerId)
        val cakeList: ArrayList<OrderDto>? = ArrayList()

        for (cake in cakeRepository.findByCustomerAndCakeStatusNot(customer, "장바구니")) {
            val od = orderdetailRepository.findByCake(cake)

            cakeList?.add(OrderDto(od.order?.orderId, cake.customer.user.userName, cake.shop.shopName, cake))
        }

        return cakeList
    }

    override fun getCustomerOrderDetail(CakeId: Long): OrderDto {
        val cake = cakeRepository.findByCakeId(CakeId)
        val od = orderdetailRepository.findByCake(cake)

        var orderDto = OrderDto(od.order?.orderId, cake.customer.user.userName, cake.shop.shopName, cake)

        return orderDto
    }


    override fun changeCakeStateCancel(CakeId: Long) {
        val cake = cakeRepository.findByCakeId(CakeId)
        val statusCheck = CakeStatusType.CANCEL.cakeStatusName

        if (cake.cakeStatus.equals(statusCheck)) {
            //TODO: 이미 주문 취소일 경우 예외 처리
        } else {
            println("왜애애애애애애")
            cake.cakeStatus = statusCheck
            cakeRepository.save(cake)

        }
    }




//    override fun getShopBySellerId(id: Long): Shop? {
//        val a = shopRepository.findBySellerId(id)?.shopId
//        return shopRepository.findBySellerId(id)
//    }


    //    override fun getCakeByCustomerId(id: Long): List<Cake>? {
//        return orderRepository.findCakeByCustomerId(id)
//    }
//    override fun getOrdersByCustomerId(id: Long): List<Orders>? {
//        var orders : List<Orders>? = orderRepository.findOrdersByCustomerId(id)
//        if(orders == null) {
//            return null;
//        }
//        for (o in orders) {
//            getOrderDetailByOrder(o)
//        }
//        return orders;
//    }




}
