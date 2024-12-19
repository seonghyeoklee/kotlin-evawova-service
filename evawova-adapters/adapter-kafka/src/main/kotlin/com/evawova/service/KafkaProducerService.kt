package com.evawova.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) {
    fun send(
        topic: String,
        message: String,
    ) {
        kafkaTemplate.send(topic, message)
    }
}
