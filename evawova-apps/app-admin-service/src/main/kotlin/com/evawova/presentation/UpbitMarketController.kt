package com.evawova.presentation

import com.evawova.upbit.market.MarketResponse
import com.evawova.upbit.market.UpbitMarketFetchUsecase
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
    ): List<MarketResponse> = upbitMarketFetchUsecase.getUpbitMarkets(isDetails)
}
