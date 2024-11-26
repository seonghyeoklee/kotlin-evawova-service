package com.evawova.event

import com.evawova.entity.admin.AdminUserEntity
import java.time.LocalDateTime

abstract class AdminUserEvent(
    private val adminUserEntity: AdminUserEntity,
) : DomainEvent {
    override fun aggregateType(): AggregateType = AggregateType.ADMIN_USER

    override fun aggregateId(): Long = adminUserEntity.id!!

    abstract override fun occurredAt(): LocalDateTime

    override fun ownerId(): Long = adminUserEntity.id!!
}
