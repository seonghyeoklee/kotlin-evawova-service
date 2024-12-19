package com.evawova.repository.upbit.market

import com.evawova.upbit.market.MarketFetchPort
import com.evawova.upbit.market.MarketResponse
import org.springframework.stereotype.Repository

@Repository
class MarketRepository(
    private val marketJpaRepository: MarketJpaRepository,
) : MarketFetchPort {
    override fun getMarkets(): List<MarketResponse> {
        TODO("Not yet implemented")
    }
}
