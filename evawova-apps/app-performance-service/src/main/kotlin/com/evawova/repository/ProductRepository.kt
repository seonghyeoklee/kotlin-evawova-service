package com.evawova.repository

import com.evawova.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByNameContaining(name: String): List<Product>

    fun findByStatus(status: String): List<Product>
}
