import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "최근 체결 내역")
data class UpbitTradeTickResponse(
    @Schema(description = "종목 코드", example = "KRW-BTC")
    @JsonProperty("market")
    val market: String,
    @Schema(description = "체결 일자(UTC 기준)", example = "2024-12-26")
    @JsonProperty("trade_date_utc")
    val tradeDateUtc: String,
    @Schema(description = "체결 시각(UTC 기준)", example = "15:30:45")
    @JsonProperty("trade_time_utc")
    val tradeTimeUtc: String,
    @Schema(description = "체결 타임스탬프", example = "1700000000000")
    @JsonProperty("timestamp")
    val timestamp: Long,
    @Schema(description = "체결 가격", example = "50000.0")
    @JsonProperty("trade_price")
    val tradePrice: Double,
    @Schema(description = "체결량", example = "0.1234")
    @JsonProperty("trade_volume")
    val tradeVolume: Double,
    @Schema(description = "전일 종가(UTC 0시 기준)", example = "49500.0")
    @JsonProperty("prev_closing_price")
    val prevClosingPrice: Double,
    @Schema(description = "변화량", example = "500.0")
    @JsonProperty("change_price")
    val changePrice: Double,
    @Schema(description = "매도/매수", example = "ASK")
    @JsonProperty("ask_bid")
    val askBid: String,
    @Schema(description = "체결 번호(Unique)", example = "17000000000001")
    @JsonProperty("sequential_id")
    val sequentialId: Long,
)
