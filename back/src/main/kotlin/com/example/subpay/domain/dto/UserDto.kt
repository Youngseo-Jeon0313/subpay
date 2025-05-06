package com.example.subpay.domain.dto

import java.time.LocalDateTime

class UserDto {
    data class Request(
        val name: String,
        val email: String,
        val password: String
    )

    data class Response(
        val id: Long,
        val name: String,
        val email: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    )
}
