package com.evawova.upbit.external

import com.evawova.feign.upbit.UpbitMarketFeignClient
import com.evawova.upbit.candle.CandleSecondResponse
import com.evawova.upbit.market.UpbitMarketFetchUsecase
import com.evawova.upbit.market.UpbitMarketResponse
import com.evawova.upbit.ticker.UpbitTickerResponse
import org.springframework.stereotype.Service

@Service
class UpbitMarketService(
    private val upbitMarketFeignClient: UpbitMarketFeignClient,
) : UpbitMarketFetchUsecase {
    override fun getUpbitMarkets(isDetails: Boolean): List<UpbitMarketResponse> = upbitMarketFeignClient.getUpbitMarkets(isDetails)

    override fun getUpbitTicker(markets: String?): List<UpbitTickerResponse> {
        val resolvedMarkets = resolveMarkets(markets)
        return upbitMarketFeignClient.getUpbitTicker(resolvedMarkets)
    }

    override fun getUpbitTickerAll(quoteCurrencies: String): List<UpbitTickerResponse> =
        upbitMarketFeignClient.getUpbitTickerAll(quoteCurrencies)

    override fun getUpbitCandlesSeconds(
        market: String,
        to: String?,
        count: Int?,
    ): List<CandleSecondResponse> = upbitMarketFeignClient.getUpbitCandlesSeconds(market, to, count)

    private fun resolveMarkets(markets: String?): String =
        if (markets.isNullOrBlank()) {
            this
                .getUpbitMarkets(false)
                .joinToString(",") { it.market }
        } else {
            markets
        }
}
