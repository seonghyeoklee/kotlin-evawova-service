package com.evawova.upbit.external

import UpbitTradeTickResponse
import com.evawova.feign.upbit.UpbitMarketFeignClient
import com.evawova.upbit.candle.UpbitCandleDayResponse
import com.evawova.upbit.candle.UpbitCandleMinuteResponse
import com.evawova.upbit.candle.UpbitCandleMonthResponse
import com.evawova.upbit.candle.UpbitCandleSecondResponse
import com.evawova.upbit.candle.UpbitCandleWeekResponse
import com.evawova.upbit.candle.UpbitCandleYearResponse
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
    ): List<UpbitCandleSecondResponse> = upbitMarketFeignClient.getUpbitCandlesSeconds(market, to, count)

    override fun getUpbitCandlesMinutes(
        market: String,
        to: String?,
        count: Int?,
        unit: Int,
    ): List<UpbitCandleMinuteResponse> = upbitMarketFeignClient.getUpbitCandlesMinutes(market, to, count, unit)

    override fun getUpbitCandlesDays(
        market: String,
        to: String?,
        count: Int?,
        convertingPriceUnit: String?,
    ): List<UpbitCandleDayResponse> = upbitMarketFeignClient.getUpbitCandlesDays(market, to, count, convertingPriceUnit)

    override fun getUpbitCandlesWeeks(
        market: String,
        to: String?,
        count: Int?,
    ): List<UpbitCandleWeekResponse> = upbitMarketFeignClient.getUpbitCandlesWeeks(market, to, count)

    override fun getUpbitCandlesMonths(
        market: String,
        to: String?,
        count: Int?,
    ): List<UpbitCandleMonthResponse> = upbitMarketFeignClient.getUpbitCandlesMonths(market, to, count)

    override fun getUpbitCandlesYears(
        market: String,
        to: String?,
        count: Int?,
    ): List<UpbitCandleYearResponse> = upbitMarketFeignClient.getUpbitCandlesYears(market, to, count)

    override fun getUpbitTradeTicks(
        market: String,
        to: String?,
        count: Int?,
        cursor: String?,
        daysAgo: Int?,
    ): List<UpbitTradeTickResponse> = upbitMarketFeignClient.getUpbitTradesTicks(market, to, count, cursor, daysAgo)

    private fun resolveMarkets(markets: String?): String =
        if (markets.isNullOrBlank()) {
            this
                .getUpbitMarkets(false)
                .joinToString(",") { it.market }
        } else {
            markets
        }
}
