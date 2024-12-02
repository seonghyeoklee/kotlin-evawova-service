package com.evawova.service

import com.evawova.admin.KafkaProducerPort
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) : KafkaProducerPort {
    override fun send(
        topic: String,
        message: String,
    ) {
        kafkaTemplate.send(topic, message)
    }
}
