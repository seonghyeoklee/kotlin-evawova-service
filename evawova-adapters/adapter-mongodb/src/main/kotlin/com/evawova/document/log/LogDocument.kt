package com.evawova.document.log

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "logs")
data class LogDocument(
    @Id
    val id: String? = null,
    val level: LogLevel,
    val message: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
