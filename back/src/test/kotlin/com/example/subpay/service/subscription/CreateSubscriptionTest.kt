package com.example.subpay.service.subscription

import com.example.subpay.domain.Subscription
import com.example.subpay.domain.dto.SubscriptionDto
import com.example.subpay.fixture.ProductFixture
import com.example.subpay.fixture.SubscriptionFixture
import com.example.subpay.fixture.UserFixture
import com.example.subpay.repository.SubscriptionRepository
import com.example.subpay.service.SubscriptionService
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

class CreateSubscriptionTest : BehaviorSpec({

    val subscriptionRepository = mockk<SubscriptionRepository>()
    val subscriptionService = SubscriptionService(subscriptionRepository)

    given("유저가 어떤 상품에 대한 구독을 신청한다.") {
        val user = UserFixture.generate()
        val now = LocalDateTime.now()

        `when`("해당 상품을 이미 구독하고 있다면") {
            val product = ProductFixture.generate()
            val subscriptionMock = SubscriptionFixture.generate(
                userId = user.id,
                productId = product.id,
                subscriptionDate = now,
                subscriptionExpirationDate = now,
                subscriptionStatus = Subscription.SubscriptionStatus.INACTIVE,
                subscriptionCycleType = Subscription.SubscriptionCycleType.DAILY,
                cycleDetails = null
            )
            every { subscriptionRepository.findByUserIdAndProductId(user.id!!, product.id!!) } returns subscriptionMock

            val exception = shouldThrowExactly<IllegalArgumentException> {
                subscriptionService.createSubscription(
                    SubscriptionDto.Request(
                        userId = user.id!!,
                        productId = product.id!!,
                        subscriptionDate = now,
                        subscriptionExpirationDate = now.plusMonths(3),
                        subscriptionStatus = "INACTIVE",
                        subscriptionCycleType = "DAILY",
                        cycleDetails = null,
                        subscriptionPrice = 10000L
                    )
                )
            }
            exception.message shouldBe "이미 구독하고 있는 상품입니다."
        }
    }
})
