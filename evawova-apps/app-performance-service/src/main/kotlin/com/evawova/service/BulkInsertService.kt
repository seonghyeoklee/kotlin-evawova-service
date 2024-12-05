package com.evawova.service

import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.util.*

@Service
class BulkInsertService(
    private val jdbcTemplate: JdbcTemplate,
) {
    private val logger = LoggerFactory.getLogger(BulkInsertService::class.java)

    fun bulkInsertProducts(
        batchSize: Int = 10000,
        totalRecords: Int = 1_000_000,
    ) {
        val random = Random()
        val sql =
            """
            INSERT INTO products (name, brand, price, stock_quantity, sale_start_date, sale_end_date, description, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """.trimIndent()

        var recordsInserted = 0

        while (recordsInserted < totalRecords) {
            val batchValues = mutableListOf<Array<Any?>>()
            for (i in 1..batchSize) {
                val randomStock = random.nextInt(1000)
                val randomPrice = BigDecimal(random.nextDouble() * 500).setScale(2, RoundingMode.HALF_UP)

                batchValues.add(
                    arrayOf(
                        "Product-${UUID.randomUUID()}",
                        "Brand-${random.nextInt(50)}",
                        randomPrice,
                        randomStock,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(random.nextLong(365)),
                        "Random description for product",
                        if (randomStock > 0) "AVAILABLE" else "OUT_OF_STOCK",
                    ),
                )
            }
            jdbcTemplate.batchUpdate(sql, batchValues)
            recordsInserted += batchValues.size
            logger.info("Inserted $recordsInserted / $totalRecords records")
        }
        logger.info("Bulk insert completed successfully!")
    }
}
