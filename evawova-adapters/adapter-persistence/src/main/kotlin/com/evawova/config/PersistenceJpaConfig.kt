package com.evawova.config

import com.evawova.entity.EntityModule
import com.evawova.repository.RepositoryModule
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackageClasses = [EntityModule::class])
@EnableJpaRepositories(basePackageClasses = [RepositoryModule::class])
class PersistenceJpaConfig {
    @Bean
    fun jpaQueryFactory(entityManager: EntityManager): JPAQueryFactory = JPAQueryFactory(entityManager)
}
