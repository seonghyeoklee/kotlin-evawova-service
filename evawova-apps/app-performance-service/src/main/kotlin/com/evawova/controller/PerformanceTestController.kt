package com.evawova.controller

import com.evawova.entity.Product
import com.evawova.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PerformanceTestController(
    private val productService: ProductService,
) {
    @GetMapping("/products")
    fun insert(): Product = productService.getProductById(100)
}
