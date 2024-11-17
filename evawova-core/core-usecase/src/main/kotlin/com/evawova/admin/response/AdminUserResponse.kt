package com.evawova.admin.response

data class AdminUserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val role: String,
    val status: String,
)
