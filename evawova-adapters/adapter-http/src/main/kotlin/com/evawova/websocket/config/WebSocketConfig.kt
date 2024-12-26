package com.evawova.websocket.config

import com.evawova.websocket.upbit.UpbitOrderBookHandler
import com.evawova.websocket.upbit.UpbitTickerHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val upbitTickerHandler: UpbitTickerHandler,
    private val upbitOrderBookHandler: UpbitOrderBookHandler,
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(upbitTickerHandler, "/ws/upbit/ticker").setAllowedOrigins("*")
        registry.addHandler(upbitOrderBookHandler, "/ws/upbit/orderbook").setAllowedOrigins("*")
    }
}