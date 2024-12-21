package com.evawova.upbit.market

interface UpbitMarketFetchUsecase {
    fun getUpbitMarkets(isDetails: Boolean): List<MarketResponse>
}
