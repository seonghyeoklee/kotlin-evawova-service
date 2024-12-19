package com.evawova.feign.upbit

import com.evawova.upbit.market.MarketResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "upbitMarketClient", url = "https://api.upbit.com/v1")
interface UpbitMarketClient {
    @GetMapping("/market/all")
    fun getMarkets(
        @RequestParam("is_details") isDetails: Boolean = true,
    ): List<MarketResponse>
}
