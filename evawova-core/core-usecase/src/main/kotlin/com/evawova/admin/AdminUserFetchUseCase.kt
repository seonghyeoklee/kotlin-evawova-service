package com.evawova.admin

import com.evawova.admin.response.AdminUserResponse

interface AdminUserFetchUseCase {
    fun fetchAdminUser(): AdminUserResponse
}
