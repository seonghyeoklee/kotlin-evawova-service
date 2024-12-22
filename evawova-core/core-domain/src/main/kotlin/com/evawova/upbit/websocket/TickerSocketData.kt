package com.evawova.upbit.websocket

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Ticker Data Schema")
data class TickerSocketData(
    @Schema(description = "데이터 타입 (ticker)")
    @JsonProperty("type")
    val type: String,
    @Schema(description = "마켓 코드 (ex. KRW-BTC)")
    @JsonProperty("code")
    val code: String,
    @Schema(description = "시가")
    @JsonProperty("opening_price")
    val openingPrice: Double,
    @Schema(description = "고가")
    @JsonProperty("high_price")
    val highPrice: Double,
    @Schema(description = "저가")
    @JsonProperty("low_price")
    val lowPrice: Double,
    @Schema(description = "현재가")
    @JsonProperty("trade_price")
    val tradePrice: Double,
    @Schema(description = "전일 종가")
    @JsonProperty("prev_closing_price")
    val prevClosingPrice: Double,
    @Schema(description = "전일 대비 (RISE, EVEN, FALL)")
    @JsonProperty("change")
    val change: String,
    @Schema(description = "부호 없는 전일 대비 값")
    @JsonProperty("change_price")
    val changePrice: Double,
    @Schema(description = "전일 대비 값")
    @JsonProperty("signed_change_price")
    val signedChangePrice: Double,
    @Schema(description = "부호 없는 전일 대비 등락율")
    @JsonProperty("change_rate")
    val changeRate: Double,
    @Schema(description = "전일 대비 등락율")
    @JsonProperty("signed_change_rate")
    val signedChangeRate: Double,
    @Schema(description = "가장 최근 거래량")
    @JsonProperty("trade_volume")
    val tradeVolume: Double,
    @Schema(description = "누적 거래량 (UTC 0시 기준)")
    @JsonProperty("acc_trade_volume")
    val accTradeVolume: Double,
    @Schema(description = "24시간 누적 거래량")
    @JsonProperty("acc_trade_volume_24h")
    val accTradeVolume24h: Double,
    @Schema(description = "누적 거래대금 (UTC 0시 기준)")
    @JsonProperty("acc_trade_price")
    val accTradePrice: Double,
    @Schema(description = "24시간 누적 거래대금")
    @JsonProperty("acc_trade_price_24h")
    val accTradePrice24h: Double,
    @Schema(description = "최근 거래 일자 (UTC)")
    @JsonProperty("trade_date")
    val tradeDate: String,
    @Schema(description = "최근 거래 시각 (UTC)")
    @JsonProperty("trade_time")
    val tradeTime: String,
    @Schema(description = "체결 타임스탬프 (milliseconds)")
    @JsonProperty("trade_timestamp")
    val tradeTimestamp: Long,
    @Schema(description = "매수/매도 구분 (ASK, BID)")
    @JsonProperty("ask_bid")
    val askBid: String,
    @Schema(description = "누적 매도량")
    @JsonProperty("acc_ask_volume")
    val accAskVolume: Double,
    @Schema(description = "누적 매수량")
    @JsonProperty("acc_bid_volume")
    val accBidVolume: Double,
    @Schema(description = "52주 최고가")
    @JsonProperty("highest_52_week_price")
    val highest52WeekPrice: Double?,
    @Schema(description = "52주 최고가 달성일 (yyyy-MM-dd)")
    @JsonProperty("highest_52_week_date")
    val highest52WeekDate: String?,
    @Schema(description = "52주 최저가")
    @JsonProperty("lowest_52_week_price")
    val lowest52WeekPrice: Double?,
    @Schema(description = "52주 최저가 달성일 (yyyy-MM-dd)")
    @JsonProperty("lowest_52_week_date")
    val lowest52WeekDate: String?,
    @Schema(description = "거래 상태 (PREVIEW, ACTIVE, DELISTED)")
    @JsonProperty("market_state")
    val marketState: String,
    @Schema(description = "유의 종목 여부 (NONE, CAUTION)")
    @JsonProperty("market_warning")
    val marketWarning: String,
    @Schema(description = "타임스탬프 (milliseconds)")
    @JsonProperty("timestamp")
    val timestamp: Long,
    @Schema(description = "스트림 타입 (SNAPSHOT, REALTIME)")
    @JsonProperty("stream_type")
    val streamType: String,
)
