package com.example.subpay.service.subscription

import com.example.subpay.domain.Subscription
import com.example.subpay.fixture.SubscriptionFixture
import com.example.subpay.fixture.UserFixture
import com.example.subpay.repository.SubscriptionRepository
import com.example.subpay.service.SubscriptionService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class CreateSubscriptionTest : BehaviorSpec({

    val subscriptionRepository = mockk<SubscriptionRepository>()
    val subscriptionService = SubscriptionService(subscriptionRepository)

    given("유저가 어떤 상품에 대한 구독을 신청한다.") {
        val user = UserFixture.generate();

        `when`("해당 상품이 존재한다면") {
            val product = SubscriptionFixture.generate()
            val subscriptionMock = SubscriptionFixture.generate(
                userId = user.id,
                productId = product.id,
                subscriptionDate = null,
                subscriptionExpirationDate = null,
                subscriptionStatus = Subscription.SubscriptionStatus.INACTIVE,
                subscriptionCycleType = Subscription.SubscriptionCycleType.DAILY,
                cycleDetails = null,
            )
            then("구독 신청이 완료된다.") {
                subscriptionRepository.save(subscriptionMock).userId shouldBe subscriptionMock.userId
            }
        }

        `when`("해당 상품을 이미 구독하고 있다면") {
            then("구독 신청이 실패하고, 메시지를 반환한다.") {
            }
        }
    }
})