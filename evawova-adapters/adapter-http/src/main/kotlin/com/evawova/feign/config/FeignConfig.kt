package com.evawova.feign.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.evawova"])
class FeignConfig {
    @Bean
    fun objectMapper(): ObjectMapper = jacksonObjectMapper()
}
