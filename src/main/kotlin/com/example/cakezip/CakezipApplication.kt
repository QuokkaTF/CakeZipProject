package com.example.cakezip

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@EnableRedisHttpSession
@EnableScheduling
@SpringBootApplication
class CakezipApplication

fun main(args: Array<String>) {
    runApplication<CakezipApplication>(*args)
}
