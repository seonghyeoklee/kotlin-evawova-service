package com.evawova.entity.admin

import com.evawova.event.AdminUserEvent
import java.time.LocalDateTime

class AdminUserCreatedEvent(
    adminUserEntity: AdminUserEntity,
) : AdminUserEvent(adminUserEntity) {
    override fun occurredAt(): LocalDateTime = LocalDateTime.now()
}
