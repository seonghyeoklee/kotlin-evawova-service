package com.evawova.upbit.market

interface UpbitMarketFetchUsecase {
    fun getUpbitMarkets(): List<MarketResponse>
}
