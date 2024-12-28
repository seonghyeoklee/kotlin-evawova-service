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

    @Operation(
        summary = "업비트 초봉 데이터 조회 API",
        description = "초봉 API는요청 시점으로부터 최근 3개월 이내의 초봉을 제공합니다. 초봉 API 가 빈 리스트를 반환하거나, 요청한 개수 만큼 반환하지 않는 경우 to파라미터를 확인 해 보세요.",
    )
    @GetMapping("/upbit/candles/seconds")
    fun getUpbitCandlesSeconds(
        @Parameter(description = "마켓 코드 (ex. KRW-BTC)", required = true)
        @RequestParam(value = "market", required = true) market: String,
        @Parameter(description = "마지막 캔들 시각 (exclusive)", required = false)
        @RequestParam(value = "to", required = false) to: String?,
        @Parameter(description = "캔들 개수(최대 200개까지 요청 가능)", required = false)
        @RequestParam(value = "count", required = false) count: Int?,
    ) = upbitMarketFetchUsecase.getUpbitCandlesSeconds(market, to, count)
}
