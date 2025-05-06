package com.example.subpay.service.card

import com.example.subpay.domain.dto.CardDto
import com.example.subpay.fixture.CardFixture
import com.example.subpay.repository.CardRepository
import com.example.subpay.service.CardService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime

class CreateCardTest : BehaviorSpec({

    val cardRepository = mockk<CardRepository>()
    val cardService = CardService(cardRepository)

    given("사용자가 결제수단을 등록할 때") {
        `when`("카드 정보가 유효하다면(카드번호, 유효기간, CVV 등)") {
            val mockCard1 = CardDto.Request(
                userId = 1L,
                cardNumber = 1234567812345678,
                expirationDate = LocalDateTime.of(2025, 12, 31, 0, 0),
                cvv = 123,
                balance = 10000
            )
            every { cardRepository.countByUserId(mockCard1.userId) } returns 1
            every { cardRepository.save(any()) } returns CardFixture.generate(
                id = 1L,
                userId = mockCard1.userId,
                cardNumber = mockCard1.cardNumber,
                expirationDate = mockCard1.expirationDate,
                cvv = mockCard1.cvv,
                balance = mockCard1.balance,
                priority = 1
            )

            then("카드가 정상적으로 등록되며, 해당 카드의 우선순위는 1위가 된다") {
                cardService.registerCard(mockCard1).priority.shouldBe(1)
            }
        }

        `when`("두번째 카드를 등록하려고 하면") {
            val mockCard2 = CardDto.Request(
                userId = 1L,
                cardNumber = 2345678923456789,
                expirationDate = LocalDateTime.of(2025, 12, 31, 0, 0),
                cvv = 456,
                balance = 20000
            )
            every { cardRepository.countByUserId(mockCard2.userId) } returns 1
            every { cardRepository.save(any()) } returns CardFixture.generate(
                id = 2L,
                userId = mockCard2.userId,
                cardNumber = mockCard2.cardNumber,
                expirationDate = mockCard2.expirationDate,
                cvv = mockCard2.cvv,
                balance = mockCard2.balance,
                priority = 2
            )

            then("카드가 정상적으로 등록되며, 카드의 우선순위는 2위가 된다") {
                cardService.registerCard(mockCard2).priority.shouldBe(2)
            }
        }

        `when`("사용자가 이미 3개의 결제수단을 등록한 상태에서 또 등록하려고 하면") {
            val fourthMockCard = CardDto.Request(
                userId = 1L,
                cardNumber = 2345678123456789,
                expirationDate = LocalDateTime.of(2025, 12, 31, 0, 0),
                cvv = 123,
                balance = 10000
            )

            then("결제수단 등록은 실패해야 한다") {
                every { cardRepository.countByUserId(fourthMockCard.userId) } returns 3
                val exception = shouldThrow<IllegalArgumentException> {
                    cardService.registerCard(fourthMockCard)
                }
                exception.message shouldBe "결제수단은 최대 3개까지 등록할 수 있습니다."
            }
        }
    }
})
