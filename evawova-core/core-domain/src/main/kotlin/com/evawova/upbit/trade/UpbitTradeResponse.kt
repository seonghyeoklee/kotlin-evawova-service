import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "체결 데이터")
data class UpbitTradeResponse(
    @JsonProperty("type")
    @Schema(description = "데이터 타입 (예: trade)")
    val type: String,
    @JsonProperty("code")
    @Schema(description = "마켓 코드 (예: KRW-BTC)")
    val code: String,
    @JsonProperty("timestamp")
    @Schema(description = "타임스탬프 (밀리초 단위)")
    val timestamp: Long,
    @JsonProperty("trade_date")
    @Schema(description = "체결 일자 (UTC 기준, yyyy-MM-dd 형식)")
    val tradeDate: String,
    @JsonProperty("trade_time")
    @Schema(description = "체결 시각 (UTC 기준, HH:mm:ss 형식)")
    val tradeTime: String,
    @JsonProperty("trade_timestamp")
    @Schema(description = "체결 타임스탬프 (밀리초 단위)")
    val tradeTimestamp: Long,
    @JsonProperty("trade_price")
    @Schema(description = "체결 가격")
    val tradePrice: Double,
    @JsonProperty("trade_volume")
    @Schema(description = "체결량")
    val tradeVolume: Double,
    @JsonProperty("ask_bid")
    @Schema(description = "매수/매도 구분 (ASK: 매도, BID: 매수)")
    val askBid: String,
    @JsonProperty("prev_closing_price")
    @Schema(description = "전일 종가")
    val prevClosingPrice: Double,
    @JsonProperty("change")
    @Schema(description = "전일 대비 (RISE: 상승, EVEN: 보합, FALL: 하락)")
    val change: String,
    @JsonProperty("change_price")
    @Schema(description = "부호 없는 전일 대비 가격")
    val changePrice: Double,
    @JsonProperty("sequential_id")
    @Schema(description = "체결 번호 (Unique)")
    val sequentialId: Long,
    @JsonProperty("best_ask_price")
    @Schema(description = "최우선 매도 호가")
    val bestAskPrice: Double,
    @JsonProperty("best_ask_size")
    @Schema(description = "최우선 매도 잔량")
    val bestAskSize: Double,
    @JsonProperty("best_bid_price")
    @Schema(description = "최우선 매수 호가")
    val bestBidPrice: Double,
    @JsonProperty("best_bid_size")
    @Schema(description = "최우선 매수 잔량")
    val bestBidSize: Double,
    @JsonProperty("stream_type")
    @Schema(description = "스트림 타입 (SNAPSHOT: 스냅샷, REALTIME: 실시간)")
    val streamType: String,
)
