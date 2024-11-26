package com.evawova.event

import java.time.LocalDateTime

interface DomainEvent {
    fun aggregateType(): AggregateType

    fun aggregateId(): Long

    fun occurredAt(): LocalDateTime

    fun ownerId(): Long
}
