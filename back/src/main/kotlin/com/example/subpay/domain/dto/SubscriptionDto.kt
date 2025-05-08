package com.example.subpay.domain.dto

import java.time.LocalDateTime

class SubscriptionDto {

    // String Type으로 받고 이후 Enum으로 변환 & 강제
    data class CreateRequest(
        val userId: Long,
        val productId: Long,
        val subscriptionDate: LocalDateTime,
        val subscriptionExpirationDate: LocalDateTime,
        val subscriptionStatus: String, // "ACTIVE" 또는 "INACTIVE"
        val subscriptionCycleType: String, // "YEARLY", "MONTHLY", "WEEKLY", "BIWEEKLY", "DAILY"
        val cycleDetails: List<String>? = null, // 예: ["MONDAY", "FRIDAY"] 또는 ["03-01", "12-25"]
        val subscriptionPrice: Long
    )

    data class DeleteRequest(
        val userId: Long,
        val subscriptionId: Long
    )

    data class Response(
        val productId: Long,
        val subscriptionDate: LocalDateTime,
        val subscriptionExpirationDate: LocalDateTime,
        val subscriptionStatus: String, // "ACTIVE" 또는 "INACTIVE"
        val subscriptionCycleType: String, // "YEARLY", "MONTHLY", "WEEKLY", "BIWEEKLY", "DAILY"
    )

    data class UpdateRequest(
        val userId: Long,
        val subscriptionId: Long,
        val subscriptionDate: LocalDateTime,
        val subscriptionExpirationDate: LocalDateTime,
        val subscriptionStatus: String, // "ACTIVE" 또는 "INACTIVE"
        val subscriptionCycleType: String, // "YEARLY", "MONTHLY", "WEEKLY", "BIWEEKLY", "DAILY"
        val cycleDetails: List<String>? = null // 예: ["MONDAY", "FRIDAY"] 또는 ["03-01", "12-25"]
    )
}
