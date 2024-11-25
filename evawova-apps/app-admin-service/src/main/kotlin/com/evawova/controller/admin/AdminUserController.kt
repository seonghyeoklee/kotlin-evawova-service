package com.evawova.controller.admin

import com.evawova.admin.AdminUserFetchUseCase
import com.evawova.admin.AdminUserRegisterUseCase
import com.evawova.api.ApiResponse
import com.evawova.controller.admin.request.AdminUserRegisterRequest
import com.evawova.controller.admin.request.toCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin")
class AdminUserController(
    private val adminUserRegisterUseCase: AdminUserRegisterUseCase,
    private val adminUserFetchUseCase: AdminUserFetchUseCase,
) {
    @PostMapping("/users")
    fun registerAdminUser(
        @RequestBody request: AdminUserRegisterRequest,
    ): ResponseEntity<Any> {
        val response = adminUserRegisterUseCase.registerAdminUser(request.toCommand())

        return ResponseEntity.ok(
            ApiResponse.success(response),
        )
    }

    @GetMapping("/users")
    fun getAdminUsers(): ResponseEntity<Any> {
        val response = adminUserFetchUseCase.fetchAdminUser()

        return ResponseEntity.ok(
            ApiResponse.success(response),
        )
    }
}
