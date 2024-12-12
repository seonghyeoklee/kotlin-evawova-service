package com.evawova.service

import com.evawova.entity.Product
import com.evawova.repository.ProductRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    @Transactional
    fun createProduct(product: Product): Product = productRepository.save(product)

    @Cacheable(value = ["getProductById"], key = "#id")
    @Transactional(readOnly = true)
    fun getProductById(id: Long): Product = productRepository.findById(id).orElseThrow { IllegalArgumentException("Product not found") }

    @Transactional
    fun updateStock(
        productId: Long,
        newStock: Int,
    ): Product {
        val product = getProductById(productId)
        product.stockQuantity += 1

        return productRepository.save(product)
    }
}
