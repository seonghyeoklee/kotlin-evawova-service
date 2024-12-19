package com.evawova.presentation

import com.evawova.upbit.market.MarketResponse
import com.evawova.upbit.market.UpbitMarketFetchUsecase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UpbitMarketController(
    private val upbitMarketFetchUsecase: UpbitMarketFetchUsecase,
) {
    @GetMapping("/favicon.ico")
    fun favicon() {
    }

    @GetMapping("/api/v1/upbit/market")
    fun getUpbitMarkets(): List<MarketResponse> = upbitMarketFetchUsecase.getUpbitMarkets()
}
