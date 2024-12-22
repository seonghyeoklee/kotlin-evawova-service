package com.evawova.upbit.ticker

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Ticker 정보 응답 데이터")
data class UpbitTickerResponse(
    @Schema(description = "종목 구분 코드", example = "KRW-BTC")
    @JsonProperty("market")
    val market: String,
    @Schema(description = "최근 거래 일자 (UTC)", example = "20231219")
    @JsonProperty("trade_date")
    val tradeDate: String,
    @Schema(description = "최근 거래 시각 (UTC)", example = "143020")
    @JsonProperty("trade_time")
    val tradeTime: String,
    @Schema(description = "최근 거래 일자 (KST)", example = "20231219")
    @JsonProperty("trade_date_kst")
    val tradeDateKst: String,
    @Schema(description = "최근 거래 시각 (KST)", example = "233020")
    @JsonProperty("trade_time_kst")
    val tradeTimeKst: String,
    @Schema(description = "최근 거래 일시 (UTC, Unix Timestamp)", example = "1637330400000")
    @JsonProperty("trade_timestamp")
    val tradeTimestamp: Long,
    @Schema(description = "시가", example = "50000000.0")
    @JsonProperty("opening_price")
    val openingPrice: Double,
    @Schema(description = "고가", example = "60000000.0")
    @JsonProperty("high_price")
    val highPrice: Double,
    @Schema(description = "저가", example = "45000000.0")
    @JsonProperty("low_price")
    val lowPrice: Double,
    @Schema(description = "종가 (현재가)", example = "55000000.0")
    @JsonProperty("trade_price")
    val tradePrice: Double,
    @Schema(description = "전일 종가 (UTC 0시 기준)", example = "50000000.0")
    @JsonProperty("prev_closing_price")
    val prevClosingPrice: Double,
    @Schema(description = "변화 상태 (EVEN, RISE, FALL)", example = "RISE")
    @JsonProperty("change")
    val change: String,
    @Schema(description = "변화액의 절대값", example = "5000000.0")
    @JsonProperty("change_price")
    val changePrice: Double,
    @Schema(description = "변화율의 절대값", example = "0.1")
    @JsonProperty("change_rate")
    val changeRate: Double,
    @Schema(description = "부호가 있는 변화액", example = "5000000.0")
    @JsonProperty("signed_change_price")
    val signedChangePrice: Double,
    @Schema(description = "부호가 있는 변화율", example = "0.1")
    @JsonProperty("signed_change_rate")
    val signedChangeRate: Double,
    @Schema(description = "가장 최근 거래량", example = "0.5")
    @JsonProperty("trade_volume")
    val tradeVolume: Double,
    @Schema(description = "누적 거래대금 (UTC 0시 기준)", example = "1000000000.0")
    @JsonProperty("acc_trade_price")
    val accTradePrice: Double,
    @Schema(description = "24시간 누적 거래대금", example = "2000000000.0")
    @JsonProperty("acc_trade_price_24h")
    val accTradePrice24h: Double,
    @Schema(description = "누적 거래량 (UTC 0시 기준)", example = "50.0")
    @JsonProperty("acc_trade_volume")
    val accTradeVolume: Double,
    @Schema(description = "24시간 누적 거래량", example = "100.0")
    @JsonProperty("acc_trade_volume_24h")
    val accTradeVolume24h: Double,
    @Schema(description = "52주 신고가", example = "70000000.0")
    @JsonProperty("highest_52_week_price")
    val highest52WeekPrice: Double,
    @Schema(description = "52주 신고가 달성일", example = "2023-12-01")
    @JsonProperty("highest_52_week_date")
    val highest52WeekDate: String,
    @Schema(description = "52주 신저가", example = "30000000.0")
    @JsonProperty("lowest_52_week_price")
    val lowest52WeekPrice: Double,
    @Schema(description = "52주 신저가 달성일", example = "2023-01-01")
    @JsonProperty("lowest_52_week_date")
    val lowest52WeekDate: String,
    @Schema(description = "타임스탬프", example = "1637330400000")
    @JsonProperty("timestamp")
    val timestamp: Long,
)
