package com.evawova.controller

import com.evawova.repository.admin.AdminUserRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PerformanceTestController(
    private val userRepository: AdminUserRepository,
) {
    @PostMapping("/insert")
    fun insert() {
        // Insert performance test
    }
}
