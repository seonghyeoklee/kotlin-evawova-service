package com.evawova.websocket.upbit

import mu.KotlinLogging
import okhttp3.WebSocket
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.io.EOFException
import java.net.URI
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

@Component
class UpbitTradeHandler(
    private val upbitWebSocketClient: UpbitWebSocketClient,
) : TextWebSocketHandler() {
    private val logger = KotlinLogging.logger {}
    private val sessions = ConcurrentHashMap.newKeySet<WebSocketSession>()
    private var upbitWebSocket: WebSocket? = null
    private val reconnectDelayMillis = 1000L
    private var reconnecting = false

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
        logger.debug { "Trade Client connected: ${session.id}" }

        if (sessions.size == 1) {
            val uri: URI? = session.uri
            val queryParams =
                uri?.query?.split("&")?.associate {
                    val (key, value) = it.split("=")
                    key to value
                }

            val marketsParam = queryParams?.get("markets")
            val markets = marketsParam?.split(",") ?: emptyList()

            startFetchingTradeData(markets)
        }
    }

    override fun afterConnectionClosed(
        session: WebSocketSession,
        status: CloseStatus,
    ) {
        sessions.remove(session)
        logger.debug { "Trade Client disconnected: ${session.id}" }

        if (sessions.isEmpty()) {
            stopFetchingTradeData()
        }
    }

    override fun handleTransportError(
        session: WebSocketSession,
        exception: Throwable,
    ) {
        if (exception is EOFException) {
            logger.debug { "Trade Connection closed unexpectedly, attempting reconnect..." }
            session.close()
            attemptReconnect()
        }
    }

    private fun attemptReconnect() {
        if (reconnecting) {
            logger.info { "Trade Reconnect attempt already in progress. Skipping duplicate attempt." }
            return
        }

        reconnecting = true

        while (true) {
            try {
                logger.info { "Waiting ${reconnectDelayMillis / 1000} seconds before reconnecting..." }
                TimeUnit.MILLISECONDS.sleep(reconnectDelayMillis)

                reconnect()
                logger.info { "Reconnected successfully to Trade WebSocket." }
                break
            } catch (e: Exception) {
                logger.error(e) { "Reconnect failed. Retrying..." }
            }
        }

        reconnecting = false
    }

    private fun reconnect() {
        logger.info { "Attempting to reconnect to Trade WebSocket..." }

        val requestPayload =
            listOf(
                mapOf("ticket" to UUID.randomUUID().toString()),
                mapOf("type" to "trade", "codes" to listOf("KRW-BTC", "KRW-ETH")),
            )

        try {
            upbitWebSocket =
                upbitWebSocketClient.connect(requestPayload) { message ->
                    broadcast(message)
                }
            logger.info { "Reconnected successfully to Trade WebSocket." }
        } catch (e: Exception) {
            logger.error(e) { "Reconnect attempt failed" }
            throw IllegalStateException("Failed to reconnect to Trade WebSocket")
        }
    }

    private fun stopFetchingTradeData() {
        upbitWebSocket?.close(1000, "Trade Client disconnected")
        upbitWebSocket = null
    }

    private fun startFetchingTradeData(markets: List<String>) {
        val requestPayload =
            listOf(
                mapOf("ticket" to UUID.randomUUID()),
                mapOf("type" to "trade", "codes" to markets),
            )

        upbitWebSocket =
            upbitWebSocketClient.connect(requestPayload) { message ->
                broadcast(message)
            }
    }

    private fun broadcast(message: String) {
        sessions.forEach { session ->
            if (session.isOpen) {
                logger.debug { "Sending Trade message to session ${session.id}" }
                session.sendMessage(TextMessage(message))
            }
        }
    }
}
