package com.evawova.websocket.upbit

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

@Component
class UpbitWebSocketClient {
    private val logger = KotlinLogging.logger {}
    private val client =
        OkHttpClient
            .Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()

    private val objectMapper: ObjectMapper = jacksonObjectMapper()
    private val url = "wss://api.upbit.com/websocket/v1"

    fun connect(
        requestPayload: Any,
        onMessage: (String) -> Unit,
    ): WebSocket {
        val request = Request.Builder().url(url).build()

        val webSocket =
            client.newWebSocket(
                request,
                object : WebSocketListener() {
                    override fun onOpen(
                        webSocket: WebSocket,
                        response: Response,
                    ) {
                        logger.debug { "Connected to Upbit WebSocket" }
                        val json = objectMapper.writeValueAsString(requestPayload)
                        webSocket.send(json)
                        logger.debug { "Sent request payload: $json" }
                    }

                    override fun onMessage(
                        webSocket: WebSocket,
                        text: String,
                    ) {
                        logger.trace { "Message received text: $text" }
                        onMessage(text)
                    }

                    override fun onMessage(
                        webSocket: WebSocket,
                        bytes: ByteString,
                    ) {
                        val message = bytes.string(StandardCharsets.UTF_8)
                        logger.trace { "Message received bytes: $message" }
                        onMessage(message)
                    }

                    override fun onFailure(
                        webSocket: WebSocket,
                        t: Throwable,
                        response: Response?,
                    ) {
                        logger.error { "WebSocket failure: ${t.message}" }
                    }

                    override fun onClosed(
                        webSocket: WebSocket,
                        code: Int,
                        reason: String,
                    ) {
                        logger.debug { "WebSocket closed: $reason" }
                    }
                },
            )

        return webSocket
    }
}
