package com.evawova.admin

interface CreateAdminUserPort {
    fun createAdminUser(
        email: String,
        name: String,
        password: String,
        role: String,
    ): AdminUser
}
