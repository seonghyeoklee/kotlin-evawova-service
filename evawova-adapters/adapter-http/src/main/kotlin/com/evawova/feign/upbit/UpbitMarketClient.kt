package com.evawova.feign.upbit

import com.evawova.upbit.market.UpbitMarketResponse
import com.evawova.upbit.ticker.UpbitTickerResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "upbitMarketClient", url = "https://api.upbit.com")
interface UpbitMarketClient {
    @GetMapping("/v1/market/all")
    fun getUpbitMarkets(
        @RequestParam("is_details") isDetails: Boolean = false,
    ): List<UpbitMarketResponse>

    @GetMapping("/v1/ticker")
    fun getUpbitTicker(
        @RequestParam("markets") markets: String,
    ): List<UpbitTickerResponse>
}
