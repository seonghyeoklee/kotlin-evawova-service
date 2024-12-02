package com.evawova.admin

interface KafkaProducerPort {
    fun send(
        topic: String,
        message: String,
    )
}
