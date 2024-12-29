package com.evawova.presentation

import UpbitTradeTickResponse
import com.evawova.upbit.candle.UpbitCandleDayResponse
import com.evawova.upbit.candle.UpbitCandleMinuteResponse
import com.evawova.upbit.candle.UpbitCandleMonthResponse
import com.evawova.upbit.candle.UpbitCandleSecondResponse
import com.evawova.upbit.candle.UpbitCandleWeekResponse
import com.evawova.upbit.candle.UpbitCandleYearResponse
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
    ): List<UpbitCandleSecondResponse> = upbitMarketFetchUsecase.getUpbitCandlesSeconds(market, to, count)

    @Operation(
        summary = "업비트 분봉 데이터 조회 API",
        description = "업비트의 분봉 데이터를 조회합니다.",
    )
    @GetMapping("/upbit/candles/minutes")
    fun getUpbitCandlesMinutes(
        @Parameter(description = "마켓 코드 (ex. KRW-BTC)", required = true)
        @RequestParam(value = "market", required = true) market: String,
        @Parameter(description = "마지막 캔들 시각 (exclusive)", required = false)
        @RequestParam(value = "to", required = false) to: String?,
        @Parameter(description = "캔들 개수(최대 200개까지 요청 가능)", required = false)
        @RequestParam(value = "count", required = false) count: Int?,
    ): List<UpbitCandleMinuteResponse> = upbitMarketFetchUsecase.getUpbitCandlesMinutes(market, to, count)

    @Operation(
        summary = "업비트 일봉 데이터 조회 API",
        description = "업비트의 일봉 데이터를 조회합니다.",
    )
    @GetMapping("/upbit/candles/days")
    fun getUpbitCandlesDays(
        @Parameter(description = "마켓 코드 (ex. KRW-BTC)", required = true)
        @RequestParam(value = "market", required = true) market: String,
        @Parameter(description = "마지막 캔들 시각 (exclusive)", required = false)
        @RequestParam(value = "to", required = false) to: String?,
        @Parameter(description = "캔들 개수(최대 200개까지 요청 가능)", required = false)
        @RequestParam(value = "count", required = false) count: Int?,
        @Parameter(description = "종가 환산 화폐 단위 (생략 가능, KRW로 명시할 시 원화 환산 가격을 반환.)", required = false)
        @RequestParam(value = "converting_price_unit", required = false) convertingPriceUnit: String?,
    ): List<UpbitCandleDayResponse> = upbitMarketFetchUsecase.getUpbitCandlesDays(market, to, count, convertingPriceUnit)

    @Operation(
        summary = "업비트 주봉 데이터 조회 API",
        description = "업비트의 주봉 데이터를 조회합니다.",
    )
    @GetMapping("/upbit/candles/weeks")
    fun getUpbitCandlesWeeks(
        @Parameter(description = "마켓 코드 (ex. KRW-BTC)", required = true)
        @RequestParam(value = "market", required = true) market: String,
        @Parameter(description = "마지막 캔들 시각 (exclusive)", required = false)
        @RequestParam(value = "to", required = false) to: String?,
        @Parameter(description = "캔들 개수(최대 200개까지 요청 가능)", required = false)
        @RequestParam(value = "count", required = false) count: Int?,
    ): List<UpbitCandleWeekResponse> = upbitMarketFetchUsecase.getUpbitCandlesWeeks(market, to, count)

    @Operation(
        summary = "업비트 월봉 데이터 조회 API",
        description = "업비트의 월봉 데이터를 조회합니다.",
    )
    @GetMapping("/upbit/candles/months")
    fun getUpbitCandlesMonths(
        @Parameter(description = "마켓 코드 (ex. KRW-BTC)", required = true)
        @RequestParam(value = "market", required = true) market: String,
        @Parameter(description = "마지막 캔들 시각 (exclusive)", required = false)
        @RequestParam(value = "to", required = false) to: String?,
        @Parameter(description = "캔들 개수(최대 200개까지 요청 가능)", required = false)
        @RequestParam(value = "count", required = false) count: Int?,
    ): List<UpbitCandleMonthResponse> = upbitMarketFetchUsecase.getUpbitCandlesMonths(market, to, count)

    @Operation(
        summary = "업비트 년봉 데이터 조회 API",
        description = "업비트의 년봉 데이터를 조회합니다.",
    )
    @GetMapping("/upbit/candles/years")
    fun getUpbitCandlesYears(
        @Parameter(description = "마켓 코드 (ex. KRW-BTC)", required = true)
        @RequestParam(value = "market", required = true) market: String,
        @Parameter(description = "마지막 캔들 시각 (exclusive)", required = false)
        @RequestParam(value = "to", required = false) to: String?,
        @Parameter(description = "캔들 개수(최대 200개까지 요청 가능)", required = false)
        @RequestParam(value = "count", required = false) count: Int?,
    ): List<UpbitCandleYearResponse> = upbitMarketFetchUsecase.getUpbitCandlesYears(market, to, count)

    @Operation(
        summary = "업비트 체결 데이터 조회 API",
        description = "업비트의 체결 데이터를 조회합니다.",
    )
    @GetMapping("/upbit/trades/ticks")
    fun getUpbitTradeTicks(
        @Parameter(description = "마켓 코드 (ex. KRW-BTC)", required = true)
        @RequestParam(value = "market", required = true) market: String,
        @Parameter(description = "마지막 체결 시각. 형식 : [HHmmss 또는 HH:mm:ss]. 비워서 요청시 가장 최근 데이터", required = false)
        @RequestParam(value = "to", required = false) to: String?,
        @Parameter(description = "체결 개수(최대 200개까지 요청 가능)", required = false)
        @RequestParam(value = "count", required = false) count: Int?,
        @Parameter(description = "페이지네이션 커서 (sequential_id)", required = false)
        @RequestParam(value = "cursor", required = false) cursor: String?,
        @Parameter(description = "최근 체결 날짜 기준 7일 이내의 이전 데이터 조회 가능. 비워서 요청 시 가장 최근 체결 날짜 반환. (범위: 1 ~ 7))", required = false)
        @RequestParam(value = "days_ago", required = false) daysAgo: Int?,
    ): List<UpbitTradeTickResponse> = upbitMarketFetchUsecase.getUpbitTradeTicks(market, to, count, cursor, daysAgo)
}
