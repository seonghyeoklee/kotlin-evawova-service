package com.evawova.repository.admin

import com.evawova.admin.AdminUser
import com.evawova.admin.ReadAdminUserPort
import org.springframework.stereotype.Repository

@Repository
class ProductAdminUserRepository(
    private val adminUserMongoRepository: AdminUserMongoRepository,
) : ReadAdminUserPort {
    override fun readAdminUser(): AdminUser {
        val first = adminUserMongoRepository.findAll().first()
        return AdminUser(
            id = 1L,
            email = "123",
            name = "123",
            role = "123",
            status = "123",
        )
    }
}
