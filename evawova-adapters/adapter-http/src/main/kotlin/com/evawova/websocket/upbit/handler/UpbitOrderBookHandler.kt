package com.evawova.websocket.upbit.handler

import com.evawova.websocket.exception.InvalidAuthException
import com.evawova.websocket.exception.InvalidParamException
import com.evawova.websocket.exception.NoCodesException
import com.evawova.websocket.exception.NoTicketException
import com.evawova.websocket.exception.NoTypeException
import com.evawova.websocket.exception.WebSocketError
import com.evawova.websocket.exception.WebSocketErrorResponse
import com.evawova.websocket.exception.WrongFormatException
import com.evawova.websocket.upbit.UpbitWebSocketClient
import com.fasterxml.jackson.databind.ObjectMapper
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
class UpbitOrderBookHandler(
    private val upbitWebSocketClient: UpbitWebSocketClient,
) : TextWebSocketHandler() {
    private val logger = KotlinLogging.logger {}
    private val sessions = ConcurrentHashMap.newKeySet<WebSocketSession>()
    private var upbitWebSocket: WebSocket? = null
    private val reconnectDelayMillis = 1000L
    private var reconnecting = false

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
        logger.debug { "OrderBook Client connected: ${session.id}" }

        if (sessions.size == 1) {
            val uri: URI? = session.uri
            val queryParams =
                uri?.query?.split("&")?.associate {
                    val (key, value) = it.split("=")
                    key to value
                }

            val marketsParam = queryParams?.get("markets")
            val markets = marketsParam?.split(",") ?: emptyList()

            startFetchingOrderBook(markets)
        }
    }

    override fun afterConnectionClosed(
        session: WebSocketSession,
        status: CloseStatus,
    ) {
        sessions.remove(session)
        logger.debug { "OrderBook Client disconnected: ${session.id}" }

        if (sessions.isEmpty()) {
            stopFetchingOrderBookData()
        }
    }

    override fun handleTransportError(
        session: WebSocketSession,
        exception: Throwable,
    ) {
        if (exception is EOFException) {
            logger.debug { "OrderBook Connection closed unexpectedly, attempting reconnect..." }
            session.close()
            attemptReconnect()
        } else {
            handleError(session, exception)
        }
    }

    private fun handleError(
        session: WebSocketSession,
        exception: Throwable,
    ) {
        val errorResponse =
            when (exception) {
                is InvalidAuthException -> WebSocketErrorResponse(WebSocketError("INVALID_AUTH", exception.message!!))
                is WrongFormatException -> WebSocketErrorResponse(WebSocketError("WRONG_FORMAT", exception.message!!))
                is NoTicketException -> WebSocketErrorResponse(WebSocketError("NO_TICKET", exception.message!!))
                is NoTypeException -> WebSocketErrorResponse(WebSocketError("NO_TYPE", exception.message!!))
                is NoCodesException -> WebSocketErrorResponse(WebSocketError("NO_CODES", exception.message!!))
                is InvalidParamException -> WebSocketErrorResponse(WebSocketError("INVALID_PARAM", exception.message!!))
                else -> WebSocketErrorResponse(WebSocketError("UNKNOWN_ERROR", "알 수 없는 오류가 발생했습니다."))
            }
        session.sendMessage(TextMessage(ObjectMapper().writeValueAsString(errorResponse)))
        session.close(CloseStatus.SERVER_ERROR)
    }

    private fun attemptReconnect() {
        if (reconnecting) {
            logger.info { "OrderBook Reconnect attempt already in progress. Skipping duplicate attempt." }
            return
        }

        reconnecting = true

        while (true) {
            try {
                logger.info { "Waiting ${reconnectDelayMillis / 1000} seconds before reconnecting..." }
                TimeUnit.MILLISECONDS.sleep(reconnectDelayMillis)

                reconnect()
                logger.info { "Reconnected successfully to OrderBook WebSocket." }
                break
            } catch (e: Exception) {
                logger.error(e) { "Reconnect failed. Retrying..." }
            }
        }

        reconnecting = false
    }

    private fun reconnect() {
        logger.info { "Attempting to reconnect to OrderBook WebSocket..." }

        val requestPayload =
            listOf(
                mapOf("ticket" to UUID.randomUUID().toString()),
                mapOf("type" to "orderbook", "codes" to listOf("KRW-BTC", "KRW-ETH")),
            )

        try {
            upbitWebSocket =
                upbitWebSocketClient.connect(requestPayload) { message ->
                    broadcast(message)
                }
            logger.info { "Reconnected successfully to OrderBook WebSocket." }
        } catch (e: Exception) {
            logger.error(e) { "Reconnect attempt failed" }
            throw IllegalStateException("Failed to reconnect to OrderBook WebSocket")
        }
    }

    private fun stopFetchingOrderBookData() {
        upbitWebSocket?.close(1000, "OrderBook Client disconnected")
        upbitWebSocket = null
    }

    private fun startFetchingOrderBook(markets: List<String>) {
        val requestPayload =
            listOf(
                mapOf("ticket" to UUID.randomUUID()),
                mapOf("type" to "orderbook", "codes" to markets),
            )

        upbitWebSocket =
            upbitWebSocketClient.connect(requestPayload) { message ->
                broadcast(message)
            }
    }

    private fun broadcast(message: String) {
        sessions.forEach { session ->
            if (session.isOpen) {
                logger.debug { "Sending OrderBook message to session ${session.id}" }
                session.sendMessage(TextMessage(message))
            }
        }
    }
}
