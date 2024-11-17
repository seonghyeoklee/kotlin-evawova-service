package com.evawova.admin

import com.evawova.admin.command.AdminUserRegisterCommand
import com.evawova.admin.response.AdminUserResponse

interface AdminUserRegisterUseCase {
    fun registerAdminUser(command: AdminUserRegisterCommand): AdminUserResponse
}
