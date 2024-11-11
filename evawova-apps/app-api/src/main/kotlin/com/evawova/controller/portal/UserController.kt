package com.evawova.controller.portal

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/portal")
class UserController {
    @GetMapping("/users")
    fun getUsers(): String = "Hello, World!"
}
