package com.example.cakezip.scheduler

import com.example.cakezip.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class UserScheduler(@Autowired val userService: UserService) {

    @Scheduled(cron = "0 0 0 * * *")
    fun deleteUserFromDatabase() {
        userService.deleteUser()
    }


}