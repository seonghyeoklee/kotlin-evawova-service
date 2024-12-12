package com.evawova

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class PerformanceServiceApplication

fun main(args: Array<String>) {
    runApplication<PerformanceServiceApplication>(*args)
}
