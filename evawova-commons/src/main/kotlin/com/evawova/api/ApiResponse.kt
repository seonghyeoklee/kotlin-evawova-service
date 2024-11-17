package com.evawova.api

import com.evawova.exception.ErrorCode

class ApiResponse<T>(
    val status: Int,
    val code: String?,
    val message: String?,
    val data: T?,
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> =
            ApiResponse(
                status = 200,
                code = null,
                message = null,
                data = data,
            )

        fun <T> error(errorCode: ErrorCode): ApiResponse<T> =
            ApiResponse(
                status = errorCode.status,
                code = errorCode.code,
                message = errorCode.message,
                data = null,
            )
    }
}
