package com.evawova.upbit.market

import com.evawova.upbit.candle.CandleSecondResponse
import com.evawova.upbit.ticker.UpbitTickerResponse

interface UpbitMarketFetchUsecase {
    fun getUpbitMarkets(isDetails: Boolean): List<UpbitMarketResponse>

    fun getUpbitTicker(markets: String?): List<UpbitTickerResponse>

    fun getUpbitTickerAll(quoteCurrencies: String): List<UpbitTickerResponse>

    fun getUpbitCandlesSeconds(
        market: String,
        to: String?,
        count: Int?,
    ): List<CandleSecondResponse>
}
