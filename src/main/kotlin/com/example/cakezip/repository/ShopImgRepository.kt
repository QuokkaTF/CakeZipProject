package com.example.cakezip.repository

import com.example.cakezip.domain.shop.ShopImg
import org.springframework.data.jpa.repository.JpaRepository

interface ShopImgRepository : JpaRepository<ShopImg, Long> {
}