package com.evawova

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EvawovaApiApplication

fun main(args: Array<String>) {
    runApplication<EvawovaApiApplication>(*args)
}
