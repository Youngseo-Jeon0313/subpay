package com.example.subpay.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "payment_history")
data class PaymentHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val subscriptionId: Long? = null,
    val userId: Long? = null,
    val amount: Long = 0,

    @Enumerated(EnumType.STRING)
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING,

    val paymentDate: LocalDateTime = LocalDateTime.now(),
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    enum class PaymentStatus {
        PENDING,    // 결제 대기
        SUCCESS,    // 결제 성공
        FAILED,     // 결제 실패
        CANCELLED   // 결제 취소
    }
}
