package com.evawova.presentation

import com.evawova.upbit.market.UpbitMarketFetchUsecase
import com.evawova.upbit.market.UpbitMarketResponse
import com.evawova.upbit.ticker.UpbitTickerResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UpbitMarketController(
    private val upbitMarketFetchUsecase: UpbitMarketFetchUsecase,
) {
    @Operation(
        summary = "업비트 종목 코드 조회 API",
        description = "업비트에서 거래 가능한 종목 목록",
    )
    @GetMapping("/upbit/market")
    fun getUpbitMarkets(
        @Parameter(description = "유의종목 필드과 같은 상세 정보 노출 여부", required = true)
        @RequestParam(value = "is_details", defaultValue = "true", required = true) isDetails: Boolean,
    ): List<UpbitMarketResponse> = upbitMarketFetchUsecase.getUpbitMarkets(isDetails)

    @Operation(
        summary = "업비트 종목 단위 현재가 정보 API",
        description = "요청 당시 종목의 스냅샷을 반환한다.",
    )
    @GetMapping("/upbit/ticker")
    fun getUpbitTicker(
        @Parameter(description = "반점으로 구분되는 종목 코드 (ex. KRW-BTC, BTC-ETH)", required = false)
        @RequestParam(value = "markets", required = false) markets: String?,
    ): List<UpbitTickerResponse> = upbitMarketFetchUsecase.getUpbitTicker(markets)

    @Operation(
        summary = "업비트 마켓 단위 현재가 정보 API",
        description = "마켓 단위 종목들의 스냅샷을 반환한다.",
    )
    @GetMapping("/upbit/ticker/all")
    fun getUpbitTickerAll(
        @Parameter(description = "반점으로 구분되는 거래 화폐 코드 (ex. KRW, BTC, USDT)", required = true)
        @RequestParam(value = "quote_currencies", required = true) quoteCurrencies: String,
    ): List<UpbitTickerResponse> = upbitMarketFetchUsecase.getUpbitTickerAll(quoteCurrencies)
}
