package com.evawova.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info =
        Info(
            title = "My API",
            version = "1.0",
            description = "API for managing resources",
        ),
)
@Configuration
class SwaggerConfig
