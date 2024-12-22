package com.evawova.upbit.market

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Market 정보 응답 데이터")
data class UpbitMarketResponse(
    @Schema(description = "업비트에서 제공하는 시장 정보", example = "KRW-BTC")
    @JsonProperty("market")
    val market: String,
    @Schema(description = "디지털 자산의 한글명", example = "비트코인")
    @JsonProperty("korean_name")
    val koreanName: String,
    @Schema(description = "디지털 자산의 영문명", example = "Bitcoin")
    @JsonProperty("english_name")
    val englishName: String,
    @Schema(description = "Market Event 정보")
    @JsonProperty("market_event")
    val marketEvent: MarketEvent? = null,
)

@Schema(description = "Market Event 상세 정보")
data class MarketEvent(
    @Schema(description = "유의종목 지정 여부", example = "false")
    @JsonProperty("warning")
    val warning: Boolean,
    @Schema(description = "주의 정보 상세")
    @JsonProperty("caution")
    val caution: Caution,
)

@Schema(description = "Caution 상세 정보")
data class Caution(
    @Schema(description = "가격 변동성 여부", example = "false")
    @JsonProperty("PRICE_FLUCTUATIONS")
    val priceFluctuations: Boolean,
    @Schema(description = "거래량 급증 여부", example = "false")
    @JsonProperty("TRADING_VOLUME_SOARING")
    val tradingVolumeSoaring: Boolean,
    @Schema(description = "입금액 급증 여부", example = "false")
    @JsonProperty("DEPOSIT_AMOUNT_SOARING")
    val depositAmountSoaring: Boolean,
    @Schema(description = "글로벌 가격 차이 여부", example = "false")
    @JsonProperty("GLOBAL_PRICE_DIFFERENCES")
    val globalPriceDifferences: Boolean,
    @Schema(description = "소규모 계정 집중 여부", example = "false")
    @JsonProperty("CONCENTRATION_OF_SMALL_ACCOUNTS")
    val concentrationOfSmallAccounts: Boolean,
)
