package com.evawova.upbit.market

import org.springframework.stereotype.Service

@Service
class MarketService(
    private val marketFetchPort: MarketFetchPort,
) : MarketFetchUsecase {
    override fun getMarkets(): List<UpbitMarketResponse> = marketFetchPort.getMarkets()
}
