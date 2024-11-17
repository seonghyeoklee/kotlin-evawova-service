package com.evawova

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LoggerServiceApplication

fun main(args: Array<String>) {
    runApplication<LoggerServiceApplication>(*args)
}
