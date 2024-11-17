package com.evawova.controller.admin.request

import com.evawova.admin.command.AdminUserRegisterCommand

data class AdminUserRegisterRequest(
    val email: String,
    val name: String,
    val password: String,
    val role: String,
)

fun AdminUserRegisterRequest.toCommand() =
    AdminUserRegisterCommand(
        email = email,
        name = name,
        password = password,
        role = role,
    )
