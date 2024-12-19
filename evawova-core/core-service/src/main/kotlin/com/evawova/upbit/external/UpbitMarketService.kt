package com.evawova.upbit.external

import com.evawova.feign.upbit.UpbitMarketClient
import com.evawova.upbit.market.MarketResponse
import com.evawova.upbit.market.UpbitMarketFetchUsecase
import org.springframework.stereotype.Service

@Service
class UpbitMarketService(
    private val upbitMarketClient: UpbitMarketClient,
) : UpbitMarketFetchUsecase {
    override fun getUpbitMarkets(): List<MarketResponse> = upbitMarketClient.getMarkets()
}
