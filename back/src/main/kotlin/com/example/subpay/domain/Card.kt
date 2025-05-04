package com.example.subpay.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "card")
data class Card (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val userId: Long = 0,
    val cardNumber: Long = 0,
    val expirationDate: LocalDateTime = LocalDateTime.now(),
    val cvv: Long = 0,
    val balance: Long = 0,
    val priority: Int = 0,
) {
    init {
        require(cardNumber.toString().length == 16) { "카드 번호는 16자리여야 합니다." }
        require(cvv.toString().length == 3) { "CVV는 3자리여야 합니다." }
        require(balance >= 0) { "잔액은 0 이상이어야 합니다." }
    }
}