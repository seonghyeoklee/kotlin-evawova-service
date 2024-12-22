package com.evawova.repository.upbit.market

import com.evawova.upbit.market.MarketFetchPort
import com.evawova.upbit.market.UpbitMarketResponse
import org.springframework.stereotype.Repository

@Repository
class MarketRepository(
    private val marketJpaRepository: MarketJpaRepository,
) : MarketFetchPort {
    override fun getMarkets(): List<UpbitMarketResponse> {
        TODO("Not yet implemented")
    }
}
