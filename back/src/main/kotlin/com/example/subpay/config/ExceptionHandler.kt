package com.example.subpay.config

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
@Hidden
class SimpleExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<ApiError> {
        return ResponseEntity(
            ApiError(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                timestamp = LocalDateTime.now(),
                message = ex.message ?: "Unexpected runtime error occurred"
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ApiError> {
        return ResponseEntity(
            ApiError(
                status = HttpStatus.BAD_REQUEST.value(),
                timestamp = LocalDateTime.now(),
                message = ex.message ?: "Bad request"
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}

data class ApiError(
    val status: Int,
    val timestamp: LocalDateTime,
    val message: String
)
