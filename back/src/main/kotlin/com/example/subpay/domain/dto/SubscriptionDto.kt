package com.example.subpay.domain.dto

import java.time.LocalDateTime

data class SubscriptionDto(
    val request: Request
) {
    // String Type으로 받고 이후 Enum으로 변환 & 강제
    data class Request(
        val userId: Long,
        val productId: Long,
        val subscriptionDate: LocalDateTime,
        val subscriptionExpirationDate: LocalDateTime,
        val subscriptionStatus: String,           // "ACTIVE" 또는 "INACTIVE"
        val subscriptionCycleType: String,        // "YEARLY", "MONTHLY", "WEEKLY", "BIWEEKLY", "DAILY"
        val cycleDetails: List<String>? = null,   // 예: ["MONDAY", "FRIDAY"] 또는 ["03-01", "12-25"]
        val subscriptionPrice: Long
    )
}
