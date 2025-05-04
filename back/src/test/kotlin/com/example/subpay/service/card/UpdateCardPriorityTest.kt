package com.example.subpay.service.card

import com.example.subpay.domain.Card
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

class UpdateCardPriorityTest: BehaviorSpec({

    val cardRepository = mockk<CardRepository>()
    val cardService = CardService(cardRepository)

    given("카드 우선순위 업데이트") {

        `when`("사용자가 가진 카드 개수와 요청한 카드 개수가 일치할 때") {
            val userId = 1L
            val now = LocalDateTime.now()
            val card1 = CardFixture.generate(id = 1L, userId = userId, priority = 1, balance = 1000L,
                cardNumber = 1234567812345678L, expirationDate = now, cvv = 123)
            val card2 = CardFixture.generate(id = 2L, userId = userId, priority = 2, balance = 2000L,
                cardNumber = 2345678923456789L, expirationDate = now, cvv = 234)
            val card3 = CardFixture.generate(id = 3L, userId = userId, priority = 3, balance  = 3000L,
                cardNumber = 3456789034567890L, expirationDate = now, cvv = 345)
            val existingCards = listOf(card1, card2, card3)
            val updateRequest = CardDto.CardPriorityUpdateRequest(
                userId = userId,
                cardIds = listOf(3L, 1L, 2L) // 새로운 우선순위
            )

            beforeEach {
                every { cardRepository.findByUserId(userId) } returns existingCards
                every { cardRepository.saveAll(any<List<Card>>()) } returnsArgument 0
            }

            then("카드들의 우선순위가 정상적으로 업데이트된다") {
                val result = cardService.updateCardPriority(updateRequest)

                result.find { it.id == 3L }!!.priority shouldBe 1
                result.find { it.id == 1L }!!.priority shouldBe 2
                result.find { it.id == 2L }!!.priority shouldBe 3
            }
        }

        `when`("요청한 카드 개수가 기존 보유 카드 수와 다르면") {
            val userId = 1L
            val card1 = CardFixture.generate(id = 1L, userId = userId)
            val card2 = CardFixture.generate(id = 2L, userId = userId)

            val updateRequest = CardDto.CardPriorityUpdateRequest(
                userId = userId,
                cardIds = listOf(1L)
            )

            beforeEach {
                every { cardRepository.findByUserId(userId) } returns listOf(card1, card2)
            }

            then("예외가 발생한다") {
                val exception = shouldThrow<IllegalArgumentException> {
                    cardService.updateCardPriority(updateRequest)
                }

                exception.message shouldBe "기존 카드의 개수와 ID가 일치하지 않습니다."
            }
        }
    }
})