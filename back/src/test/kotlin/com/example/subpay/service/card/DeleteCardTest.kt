package com.example.subpay.service.card

import com.example.subpay.fixture.CardFixture
import com.example.subpay.repository.CardRepository
import com.example.subpay.service.CardService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.*

class DeleteCardTest : BehaviorSpec({

    val cardRepository = mockk<CardRepository>()
    val cardService = CardService(cardRepository)

    given("사용자가 결제 수단을 삭제할 때") {

        `when`("사용자가 등록한 카드가 하나일 경우") {
            val mockCard = CardFixture.generate(
                id = 1L,
                userId = 1L,
                cardNumber = 1234567812345678,
                expirationDate = LocalDateTime.of(2025, 12, 31, 0, 0),
                cvv = 123,
                balance = 10000L
            )

            every { cardRepository.findByUserId(mockCard.userId) } returns listOf(mockCard) // mockCard 1개 있음

            every { cardRepository.findById(mockCard.id!!) } returns Optional.of(mockCard)
            every { cardRepository.delete(mockCard) } returns Unit

            every { cardRepository.findByUserId(mockCard.userId) } returns emptyList()

            then("카드가 정상적으로 삭제되었고, 남은 카드 개수는 0개가 된다.") {
                val result = cardService.deleteCard(mockCard.id!!)
                result.size shouldBe 0
            }
        }

        `when`("삭제하려는 카드가 존재하지 않는다면") {
            val nonExistentCardId = 999L
            every { cardRepository.findById(nonExistentCardId) } returns Optional.empty()

            then("예외가 발생한다.") {
                val exception = shouldThrow<IllegalArgumentException> {
                    cardService.deleteCard(nonExistentCardId)
                }
                exception.message shouldBe "해당 결제수단이 존재하지 않습니다."
            }
        }
    }
})
