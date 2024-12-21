package com.evawova.presentation.response

import com.evawova.upbit.market.MarketEvent
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Market 정보 응답 데이터")
data class UpbitMarketResponse(
    @Schema(description = "업비트에서 제공하는 시장 정보", example = "KRW-BTC")
    val market: String,
    @Schema(description = "디지털 자산의 한글명", example = "비트코인")
    val koreanName: String,
    @Schema(description = "디지털 자산의 영문명", example = "Bitcoin")
    val englishName: String,
    @Schema(description = "Market Event 정보")
    val marketEvent: MarketEvent? = null,
)

@Schema(description = "Market Event 상세 정보")
data class MarketEvent(
    @Schema(description = "유의종목 지정 여부", example = "false")
    val warning: Boolean,
    @Schema(description = "주의 정보 상세")
    val caution: Caution,
)

@Schema(description = "Caution 상세 정보")
data class Caution(
    @Schema(description = "가격 변동성 여부", example = "false")
    val priceFluctuations: Boolean,
    @Schema(description = "거래량 급증 여부", example = "false")
    val tradingVolumeSoaring: Boolean,
    @Schema(description = "입금액 급증 여부", example = "false")
    val depositAmountSoaring: Boolean,
    @Schema(description = "글로벌 가격 차이 여부", example = "false")
    val globalPriceDifferences: Boolean,
    @Schema(description = "소규모 계정 집중 여부", example = "false")
    val concentrationOfSmallAccounts: Boolean,
)
