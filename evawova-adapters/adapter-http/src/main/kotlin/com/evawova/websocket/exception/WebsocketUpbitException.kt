package com.evawova.websocket.exception

data class WebSocketErrorResponse(
    val error: WebSocketError,
)

data class WebSocketError(
    val name: String,
    val message: String,
)

class InvalidAuthException : RuntimeException("인증 정보가 올바르지 않습니다.")

class WrongFormatException : RuntimeException("Format 이 맞지 않습니다.")

class NoTicketException : RuntimeException("티켓이 존재하지 않거나, 유효하지 않습니다.")

class NoTypeException : RuntimeException("type 필드가 존재하지 않습니다.")

class NoCodesException : RuntimeException("codes 필드가 존재하지 않습니다.")

class InvalidParamException : RuntimeException("codes 필드가 비어 있습니다.")
