package com.evawova.exception

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String,
) {
    NOT_FOUND(
        status = 404,
        code = "EVWV0000",
        message = "The requested resource was not found",
    ),
    INTERNAL_SERVER_ERROR(
        status = 500,
        code = "EVWV0001",
        message = "An internal server error occurred",
    ),
    INVALID_PARAMETER(
        status = 400,
        code = "EVWV0002",
        message = "The request parameter is invalid",
    ),
    UNAUTHORIZED(
        status = 401,
        code = "EVWV0003",
        message = "The request is unauthorized",
    ),
    FORBIDDEN(
        status = 403,
        code = "EVWV0004",
        message = "The request is forbidden",
    ),
    CONFLICT(
        status = 409,
        code = "EVWV0005",
        message = "The request conflicts with the current state of the server",
    ),
    BAD_REQUEST(
        status = 400,
        code = "EVWV0006",
        message = "The request is invalid",
    ),
    METHOD_NOT_ALLOWED(
        status = 405,
        code = "EVWV0007",
        message = "The request method is not allowed",
    ),
    UNSUPPORTED_MEDIA_TYPE(
        status = 415,
        code = "EVWV0008",
        message = "The request media type is not supported",
    ),
    TOO_MANY_REQUESTS(
        status = 429,
        code = "EVWV0009",
        message = "The request is rate limited",
    ),
    SERVICE_UNAVAILABLE(
        status = 503,
        code = "EVWV0010",
        message = "The service is unavailable",
    ),
    GATEWAY_TIMEOUT(
        status = 504,
        code = "EVWV0011",
        message = "The gateway timed out",
    ),
    UNKNOWN(
        status = 500,
        code = "EVWV9999",
        message = "An unknown error occurred",
    ),
}
