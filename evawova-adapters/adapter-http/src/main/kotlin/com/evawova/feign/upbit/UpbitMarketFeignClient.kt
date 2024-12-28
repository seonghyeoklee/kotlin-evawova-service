package com.evawova.feign.upbit

import com.evawova.upbit.candle.CandleMinuteResponse
import com.evawova.upbit.candle.CandleSecondResponse
import com.evawova.upbit.market.UpbitMarketResponse
import com.evawova.upbit.ticker.UpbitTickerResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "upbitMarketClient", url = "https://api.upbit.com")
interface UpbitMarketFeignClient {
    @GetMapping("/v1/market/all")
    fun getUpbitMarkets(
        @RequestParam("is_details") isDetails: Boolean = false,
    ): List<UpbitMarketResponse>

    @GetMapping("/v1/ticker")
    fun getUpbitTicker(
        @RequestParam("markets") markets: String,
    ): List<UpbitTickerResponse>

    @GetMapping("/v1/ticker/all")
    fun getUpbitTickerAll(
        @RequestParam("quote_currencies") quoteCurrencies: String,
    ): List<UpbitTickerResponse>

    @GetMapping("/v1/candles/seconds")
    fun getUpbitCandlesSeconds(
        @RequestParam(value = "market", required = true) market: String,
        @RequestParam(value = "to", required = false) to: String?,
        @RequestParam(value = "count", required = false) count: Int?,
    ): List<CandleSecondResponse>

    @GetMapping("/v1/candles/minutes/{unit}")
    fun getUpbitCandlesMinutes(
        @RequestParam(value = "market", required = true) market: String,
        @RequestParam(value = "to", required = false) to: String?,
        @RequestParam(value = "count", required = false) count: Int?,
        @PathVariable(value = "unit") unit: Int,
    ): List<CandleMinuteResponse>
}
