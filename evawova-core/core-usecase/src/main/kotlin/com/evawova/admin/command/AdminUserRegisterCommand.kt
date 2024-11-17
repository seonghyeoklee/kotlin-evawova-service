package com.evawova.admin.command

data class AdminUserRegisterCommand(
    val email: String,
    val name: String,
    val password: String,
    val role: String,
)
