package com.evawova.exception

import mu.KotlinLogging
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalControllerAdvice
