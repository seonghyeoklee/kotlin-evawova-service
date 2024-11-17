package com.evawova.config

import com.evawova.repository.RepositoryModule
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackageClasses = [RepositoryModule::class])
class MongoConfig
