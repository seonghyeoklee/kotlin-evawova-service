package com.evawova.upbit.market

interface MarketFetchPort {
    fun getMarkets(): List<MarketResponse>
}
