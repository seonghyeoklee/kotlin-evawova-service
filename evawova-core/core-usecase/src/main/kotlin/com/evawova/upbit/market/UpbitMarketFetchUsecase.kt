package com.evawova.upbit.market

import UpbitTradeTickResponse
import com.evawova.upbit.candle.UpbitCandleDayResponse
import com.evawova.upbit.candle.UpbitCandleMinuteResponse
import com.evawova.upbit.candle.UpbitCandleMonthResponse
import com.evawova.upbit.candle.UpbitCandleSecondResponse
import com.evawova.upbit.candle.UpbitCandleWeekResponse
import com.evawova.upbit.candle.UpbitCandleYearResponse
import com.evawova.upbit.ticker.UpbitTickerResponse

interface UpbitMarketFetchUsecase {
    fun getUpbitMarkets(isDetails: Boolean): List<UpbitMarketResponse>

    fun getUpbitTicker(markets: String?): List<UpbitTickerResponse>

    fun getUpbitTickerAll(quoteCurrencies: String): List<UpbitTickerResponse>

    fun getUpbitCandlesSeconds(
        market: String,
        to: String?,
        count: Int?,
    ): List<UpbitCandleSecondResponse>

    fun getUpbitCandlesMinutes(
        market: String,
        to: String?,
        count: Int?,
        unit: Int = 1,
    ): List<UpbitCandleMinuteResponse>

    fun getUpbitCandlesDays(
        market: String,
        to: String?,
        count: Int?,
        convertingPriceUnit: String?,
    ): List<UpbitCandleDayResponse>

    fun getUpbitCandlesWeeks(
        market: String,
        to: String?,
        count: Int?,
    ): List<UpbitCandleWeekResponse>

    fun getUpbitCandlesMonths(
        market: String,
        to: String?,
        count: Int?,
    ): List<UpbitCandleMonthResponse>

    fun getUpbitCandlesYears(
        market: String,
        to: String?,
        count: Int?,
    ): List<UpbitCandleYearResponse>

    fun getUpbitTradeTicks(
        market: String,
        to: String?,
        count: Int?,
        cursor: String?,
        daysAgo: Int?,
    ): List<UpbitTradeTickResponse>
}
