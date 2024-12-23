package com.evawova.filter

import io.micrometer.context.ContextRegistry
import org.slf4j.MDC
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Hooks
import reactor.core.publisher.Mono
import reactor.util.context.Context
import java.util.UUID

const val KEY = "traceId"
const val METHOD = "method"
const val REQUEST_URI = "requestUri"

@Component
@Order(1)
class MdcFilter : WebFilter {
    init {
        Hooks.enableAutomaticContextPropagation()
        ContextRegistry.getInstance().registerThreadLocalAccessor(
            KEY,
            { MDC.get(KEY) },
            { value -> MDC.put(KEY, value) },
            { MDC.remove(KEY) },
        )
        ContextRegistry.getInstance().registerThreadLocalAccessor(
            METHOD,
            { MDC.get(METHOD) },
            { value -> MDC.put(METHOD, value) },
            { MDC.remove(METHOD) },
        )
        ContextRegistry.getInstance().registerThreadLocalAccessor(
            REQUEST_URI,
            { MDC.get(REQUEST_URI) },
            { value -> MDC.put(REQUEST_URI, value) },
            { MDC.remove(REQUEST_URI) },
        )
    }

    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain,
    ): Mono<Void> {
        val uuid = exchange.request.headers["x-traceId"]?.firstOrNull() ?: "${UUID.randomUUID()}"
        val uri = exchange.request.uri.path
        val method = exchange.request.method.name()

        MDC.put(KEY, uuid)
        MDC.put(METHOD, method)
        MDC.put(REQUEST_URI, uri)
        return chain.filter(exchange).contextWrite {
            Context.of(KEY, uuid, REQUEST_URI, uri, METHOD, method)
        }
    }
}
