package com.evawova.upbit.external

import com.evawova.feign.upbit.UpbitMarketClient
import com.evawova.upbit.market.UpbitMarketFetchUsecase
import com.evawova.upbit.market.UpbitMarketResponse
import com.evawova.upbit.ticker.UpbitTickerResponse
import org.springframework.stereotype.Service

@Service
class UpbitMarketService(
    private val upbitMarketClient: UpbitMarketClient,
) : UpbitMarketFetchUsecase {
    override fun getUpbitMarkets(isDetails: Boolean): List<UpbitMarketResponse> = upbitMarketClient.getUpbitMarkets(isDetails)

    override fun getUpbitTicker(markets: String?): List<UpbitTickerResponse> {
        val resolvedMarkets = resolveMarkets(markets)
        return upbitMarketClient.getUpbitTicker(resolvedMarkets)
    }

    private fun resolveMarkets(markets: String?): String =
        if (markets.isNullOrBlank()) {
            this
                .getUpbitMarkets(false)
                .joinToString(",") { it.market }
        } else {
            markets
        }
}
