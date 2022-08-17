package com.example.cakezip.repository

import com.example.cakezip.domain.OrderDetail
import com.example.cakezip.domain.cake.Cake
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional


@Transactional
@Repository
interface OrderDetailRepository : JpaRepository<OrderDetail, Long> {

    //fun findByCustomerId(id: Long) : List<OrderDetail>

    //fun findByOrderId(id: Long) : OrderDetail


    @Modifying
    @Query("UPDATE cakezip.cake c INNER JOIN cakezip.order_detail d ON d.cake_id = c.cake_id SET c.cake_status=:state WHERE d.order_id=:id", nativeQuery = true)
    fun updateOrderDetailByCakeId(id: Long, state: String)




    fun findByCake(cake: Cake): OrderDetail

}