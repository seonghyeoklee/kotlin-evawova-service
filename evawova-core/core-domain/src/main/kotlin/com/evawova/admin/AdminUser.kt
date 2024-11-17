package com.evawova.admin

data class AdminUser(
    val id: Long,
    val email: String,
    val name: String,
    val role: String,
    val status: String,
)
