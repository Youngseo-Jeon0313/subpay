package com.example.subpay.fixture

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.subpay.domain.Card
import java.time.LocalDateTime

object CardFixture {
    val fixture = kotlinFixture()
    fun generate(
        id: Long? = null,
        userId: Long? = null,
        cardNumber: Long? = null,
        expirationDate: LocalDateTime? = null,
        cvv: String? = null,
        balance: Long? = null,
        priority: Int? = null,
    ): Card = fixture<Card> {
        id?.let { property(Card::id) { it } }
        userId?.let { property(Card::userId) { it } }
        cardNumber?.let { property(Card::cardNumber) { it } }
        expirationDate?.let { property(Card::expirationDate) { it } }
        cvv?.let { property(Card::cvv) { it } }
        balance?.let { property(Card::balance) { it } }
        priority?.let { property(Card::priority) { it } }
    }
}