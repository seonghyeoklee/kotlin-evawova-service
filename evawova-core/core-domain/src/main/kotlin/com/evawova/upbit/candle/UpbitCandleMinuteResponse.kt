package com.evawova.upbit.candle

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "분(Minute) 캔들")
data class UpbitCandleMinuteResponse(
    @Schema(description = "종목 코드", example = "KRW-BTC")
    @JsonProperty("market")
    val market: String,
    @Schema(description = "캔들 기준 시각 (UTC 기준)", example = "2024-12-25T00:00:00")
    @JsonProperty("candle_date_time_utc")
    val candleDateTimeUtc: String,
    @Schema(description = "캔들 기준 시각 (KST 기준)", example = "2024-12-25T09:00:00")
    @JsonProperty("candle_date_time_kst")
    val candleDateTimeKst: String,
    @Schema(description = "시가 (Opening Price)", example = "1000000.0")
    @JsonProperty("opening_price")
    val openingPrice: Double,
    @Schema(description = "고가 (High Price)", example = "1050000.0")
    @JsonProperty("high_price")
    val highPrice: Double,
    @Schema(description = "저가 (Low Price)", example = "950000.0")
    @JsonProperty("low_price")
    val lowPrice: Double,
    @Schema(description = "종가 (Trade Price)", example = "1020000.0")
    @JsonProperty("trade_price")
    val tradePrice: Double,
    @Schema(description = "마지막 틱 저장 시각 (Timestamp, 밀리초)", example = "1700000000000")
    @JsonProperty("timestamp")
    val timestamp: Long,
    @Schema(description = "누적 거래 금액 (Candle Accumulated Trade Price)", example = "150000000.0")
    @JsonProperty("candle_acc_trade_price")
    val candleAccTradePrice: Double,
    @Schema(description = "누적 거래량 (Candle Accumulated Trade Volume)", example = "1500.0")
    @JsonProperty("candle_acc_trade_volume")
    val candleAccTradeVolume: Double,
    @Schema(description = "분 단위(유닛)", example = "분 단위. 가능한 값 : 1, 3, 5, 15, 10, 30, 60, 240")
    val unit: Int,
)
