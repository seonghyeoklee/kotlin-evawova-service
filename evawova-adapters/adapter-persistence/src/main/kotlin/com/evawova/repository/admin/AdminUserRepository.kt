package com.evawova.repository.admin

import com.evawova.admin.AdminUser
import com.evawova.admin.CreateAdminUserPort
import com.evawova.entity.admin.AdminUserEntity
import org.springframework.stereotype.Repository

@Repository
class AdminUserRepository(
    private val adminUserJpaRepository: AdminUserJpaRepository,
) : CreateAdminUserPort {
    override fun createAdminUser(
        email: String,
        name: String,
        password: String,
        role: String,
    ): AdminUser {
        val adminUserEntity =
            AdminUserEntity(
                email = email,
                name = name,
                password = password,
                role = role,
                status = "ACTIVE",
            )

        return adminUserJpaRepository
            .save(adminUserEntity)
            .let { AdminUser(it.id!!, it.email, it.name, it.role, it.status) }
    }
}
