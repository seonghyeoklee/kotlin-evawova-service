package com.evawova.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, length = 255)
    val name: String,
    @Column(nullable = false, length = 255)
    val brand: String,
    @Column(nullable = false)
    val price: BigDecimal,
    @Column(nullable = false)
    var stockQuantity: Int,
    @Column(nullable = false)
    val saleStartDate: LocalDateTime,
    @Column(nullable = true)
    val saleEndDate: LocalDateTime? = null,
    @Lob
    val description: String,
    @Column(nullable = false)
    val status: String,
)
