package com.evawova

import com.evawova.service.BulkInsertService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class BulkInsertRunner(
    private val bulkInsertService: BulkInsertService,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
//        bulkInsertService.bulkInsertProducts(batchSize = 10_000, totalRecords = 1_000_000)
    }
}
