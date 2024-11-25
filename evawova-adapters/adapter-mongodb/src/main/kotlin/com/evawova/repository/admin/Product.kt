package com.evawova.repository.admin

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "products")
class Product {
    @Id
    private val id: String? = null
    private val name: String? = null
    private val price = 0.0
}
