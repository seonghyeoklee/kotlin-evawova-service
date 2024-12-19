package com.evawova.upbit.market

import com.fasterxml.jackson.annotation.JsonProperty

data class MarketResponse(
    @JsonProperty("market")
    val market: String, // 업비트에서 제공하는 시장 정보
    @JsonProperty("korean_name")
    val koreanName: String, // 디지털 자산 한글명
    @JsonProperty("english_name")
    val englishName: String, // 디지털 자산 영문명
    @JsonProperty("market_event")
    val marketEvent: MarketEvent,
)

data class MarketEvent(
    @JsonProperty("warning")
    val warning: Boolean, // 유의종목 지정 여부
    @JsonProperty("caution")
    val caution: Caution,
)

data class Caution(
    @JsonProperty("PRICE_FLUCTUATIONS")
    val priceFluctuations: Boolean, // 가격 변동성 여부
    @JsonProperty("TRADING_VOLUME_SOARING")
    val tradingVolumeSoaring: Boolean, // 거래량 급증 여부
    @JsonProperty("DEPOSIT_AMOUNT_SOARING")
    val depositAmountSoaring: Boolean, // 입금액 급증 여부
    @JsonProperty("GLOBAL_PRICE_DIFFERENCES")
    val globalPriceDifferences: Boolean, // 글로벌 가격 차이 여부
    @JsonProperty("CONCENTRATION_OF_SMALL_ACCOUNTS")
    val concentrationOfSmallAccounts: Boolean, // 소규모 계정 집중 여부
)
