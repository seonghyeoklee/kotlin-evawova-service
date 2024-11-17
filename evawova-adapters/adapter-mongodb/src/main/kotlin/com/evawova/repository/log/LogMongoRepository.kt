package com.evawova.repository.log

import com.evawova.document.log.LogDocument
import com.evawova.document.log.LogLevel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LogMongoRepository : MongoRepository<LogDocument, String> {
    fun findByLevel(valueOf: LogLevel): List<LogDocument>
}
