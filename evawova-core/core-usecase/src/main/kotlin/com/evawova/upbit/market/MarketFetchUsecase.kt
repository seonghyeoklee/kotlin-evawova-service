package com.evawova.upbit.market

interface MarketFetchUsecase {
    fun getMarkets(): List<UpbitMarketResponse>
}
