package com.evawova

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PerformanceServiceApplication

fun main(args: Array<String>) {
    runApplication<PerformanceServiceApplication>(*args)
}
