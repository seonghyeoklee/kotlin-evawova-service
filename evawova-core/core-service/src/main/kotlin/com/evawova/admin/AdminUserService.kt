package com.evawova.admin

import com.evawova.admin.command.AdminUserRegisterCommand
import com.evawova.admin.response.AdminUserResponse
import org.springframework.stereotype.Service

@Service
class AdminUserService(
    private val createAdminUserPort: CreateAdminUserPort,
    private val readAdminUserPort: ReadAdminUserPort,
) : AdminUserRegisterUseCase,
    AdminUserFetchUseCase {
    override fun registerAdminUser(command: AdminUserRegisterCommand): AdminUserResponse {
        val portalUser =
            createAdminUserPort.createAdminUser(
                email = command.email,
                name = command.name,
                password = command.password,
                role = command.role,
            )

        return AdminUserResponse(
            id = portalUser.id,
            email = portalUser.email,
            name = portalUser.name,
            role = portalUser.role,
            status = portalUser.status,
        )
    }

    override fun fetchAdminUser(): AdminUserResponse {
        val adminUser = readAdminUserPort.readAdminUser()

        return AdminUserResponse(
            id = adminUser.id,
            email = adminUser.email,
            name = adminUser.name,
            role = adminUser.role,
            status = adminUser.status,
        )
    }
}
