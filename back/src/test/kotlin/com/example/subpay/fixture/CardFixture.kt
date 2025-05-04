package com.example.subpay.fixture

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.subpay.domain.Card
import java.time.LocalDateTime
import kotlin.random.Random

object CardFixture {
    val fixture = kotlinFixture()

    fun generate(
        id: Long? = null,
        userId: Long? = null,
        cardNumber: Long? = generateCardNumber(),
        expirationDate: LocalDateTime? = LocalDateTime.now(),
        cvv: Long? = generateCvv(),
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

    private fun generateCardNumber(): Long {
        val cardNumber = StringBuilder()
        for (i in 0 until 16) {
            cardNumber.append(Random.nextInt(1, 10))
        }
        return cardNumber.toString().toLong()
    }

    private fun generateCvv(): Long {
        return Random.nextLong(100, 999)
    }
}
