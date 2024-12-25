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
class UpbitTickerHandler(
    private val upbitWebSocketClient: UpbitWebSocketClient,
) : TextWebSocketHandler() {
    private val logger = KotlinLogging.logger {}
    private val sessions = ConcurrentHashMap.newKeySet<WebSocketSession>()
    private var upbitWebSocket: WebSocket? = null
    private val reconnectDelayMillis = 1000L
    private var reconnecting = false

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
        logger.debug { "Client connected: ${session.id}" }

        if (sessions.size == 1) {
            val uri: URI? = session.uri
            val queryParams =
                uri?.query?.split("&")?.associate {
                    val (key, value) = it.split("=")
                    key to value
                }

            val marketsParam = queryParams?.get("markets")
            val markets = marketsParam?.split(",") ?: emptyList()

            startFetchingData(markets)
        }
    }

    override fun afterConnectionClosed(
        session: WebSocketSession,
        status: CloseStatus,
    ) {
        sessions.remove(session)
        logger.debug { "Client disconnected: ${session.id}" }

        if (sessions.isEmpty()) {
            stopFetchingTickerData()
        }
    }

    override fun handleTransportError(
        session: WebSocketSession,
        exception: Throwable,
    ) {
        if (exception is EOFException) {
            logger.debug { "Connection closed unexpectedly, attempting reconnect..." }
            session.close()
            attemptReconnect()
        }
    }

    private fun attemptReconnect() {
        if (reconnecting) {
            logger.info { "Reconnect attempt already in progress. Skipping duplicate attempt." }
            return
        }

        reconnecting = true

        while (true) {
            try {
                logger.info { "Waiting ${reconnectDelayMillis / 1000} seconds before reconnecting..." }
                TimeUnit.MILLISECONDS.sleep(reconnectDelayMillis)

                reconnect()
                logger.info { "Reconnected successfully." }
                break
            } catch (e: Exception) {
                logger.error(e) { "Reconnect failed. Retrying..." }
            }
        }

        reconnecting = false
    }

    private fun reconnect() {
        logger.info { "Attempting to reconnect..." }

        val requestPayload =
            listOf(
                mapOf("ticket" to UUID.randomUUID().toString()),
                mapOf("type" to "ticker", "codes" to listOf("KRW-BTC", "KRW-ETH")),
            )

        try {
            upbitWebSocket =
                upbitWebSocketClient.connect(requestPayload) { message ->
                    broadcast(message)
                }
            logger.info { "Reconnected successfully to Upbit WebSocket" }
        } catch (e: Exception) {
            logger.error(e) { "Reconnect attempt failed" }
            throw IllegalStateException("Failed to reconnect to WebSocket")
        }
    }

    private fun stopFetchingTickerData() {
        upbitWebSocket?.close(1000, "Client disconnected")
        upbitWebSocket = null
    }

    private fun startFetchingData(markets: List<String>) {
        val requestPayload =
            listOf(
                mapOf("ticket" to UUID.randomUUID()),
                mapOf("type" to "ticker", "codes" to markets),
            )

        upbitWebSocket =
            upbitWebSocketClient.connect(requestPayload) { message ->
                broadcast(message)
            }
    }

    private fun broadcast(message: String) {
        sessions.forEach { session ->
            if (session.isOpen) {
                logger.debug { "Sending message to session ${session.id}" }
                session.sendMessage(TextMessage(message))
            }
        }
    }
}
