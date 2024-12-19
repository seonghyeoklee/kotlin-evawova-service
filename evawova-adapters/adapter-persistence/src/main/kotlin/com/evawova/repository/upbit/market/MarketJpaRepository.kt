package com.evawova.repository.upbit.market

import com.evawova.entity.upbit.market.MarketEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MarketJpaRepository : JpaRepository<MarketEntity, Long>
