package com.example.subpay.service.subscription

import com.example.subpay.domain.Subscription
import com.example.subpay.domain.dto.SubscriptionDto
import com.example.subpay.fixture.ProductFixture
import com.example.subpay.fixture.SubscriptionFixture
import com.example.subpay.fixture.UserFixture
import com.example.subpay.repository.SubscriptionRepository
import com.example.subpay.service.SubscriptionService
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import java.util.*

class UpdateSubscriptionTest : BehaviorSpec({

    val subscriptionRepository = mockk<SubscriptionRepository>()
    val subscriptionService = SubscriptionService(subscriptionRepository)

    val user = UserFixture.generate()
    val now = LocalDateTime.now()

    given("구독을 연장한다.") {
        `when`("구독이 존재한다면") {
            val product = ProductFixture.generate()
            val subscriptionMock = SubscriptionFixture.generate(
                userId = user.id,
                productId = product.id,
                subscriptionDate = now,
                subscriptionExpirationDate = now.plusMonths(3),
                subscriptionStatus = Subscription.SubscriptionStatus.INACTIVE,
                subscriptionCycleType = Subscription.SubscriptionCycleType.DAILY,
                cycleDetails = null
            )

            every { subscriptionRepository.findById(any()) } returns Optional.of(subscriptionMock)

            every { subscriptionRepository.save(any()) } returns subscriptionMock

            val updateRequest = SubscriptionDto.UpdateRequest(
                userId = user.id!!,
                subscriptionId = subscriptionMock.id!!,
                subscriptionDate = now,
                subscriptionExpirationDate = now.plusMonths(6),
                subscriptionStatus = "ACTIVE",
                subscriptionCycleType = "MONTHLY",
                cycleDetails = null,
            )

            then("구독 연장이 완료된다.") {
                subscriptionService.updateSubscription(updateRequest)
                verify(exactly = 1) {
                    subscriptionRepository.findById(subscriptionMock.id!!)
                }
                verify(exactly = 1) {
                    subscriptionRepository.save(any())
                }
            }
        }
    }
})
