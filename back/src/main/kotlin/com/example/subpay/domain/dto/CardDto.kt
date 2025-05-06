package com.example.subpay.domain.dto

import java.time.LocalDateTime

class CardDto {
    data class Response(
        val id: Long,
        val userId: Long,
        val cardNumber: Long,
        val expirationDate: LocalDateTime,
        val cvv: Long,
        val balance: Long,
        val priority: Int
    )

    data class Request(
        val userId: Long,
        val cardNumber: Long,
        val expirationDate: LocalDateTime,
        val cvv: Long,
        val balance: Long
    )

    data class CardPriorityUpdateRequest(
        val userId: Long,
        val cardIds: List<Long>
    )
}
