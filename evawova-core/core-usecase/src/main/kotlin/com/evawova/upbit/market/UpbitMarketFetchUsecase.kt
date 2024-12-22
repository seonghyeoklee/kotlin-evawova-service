package com.evawova.upbit.market

import com.evawova.upbit.ticker.UpbitTickerResponse

interface UpbitMarketFetchUsecase {
    fun getUpbitMarkets(isDetails: Boolean): List<UpbitMarketResponse>

    fun getUpbitTicker(markets: String?): List<UpbitTickerResponse>
}
