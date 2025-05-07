package com.example.subpay.service.subscription

import com.example.subpay.domain.Subscription
import com.example.subpay.domain.dto.SubscriptionDto
import com.example.subpay.fixture.ProductFixture
import com.example.subpay.fixture.SubscriptionFixture
import com.example.subpay.fixture.UserFixture
import com.example.subpay.repository.SubscriptionRepository
import com.example.subpay.service.SubscriptionService
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime

class DeleteSubscriptionTest : BehaviorSpec({

    val subscriptionRepository = mockk<SubscriptionRepository>(relaxed = true)
    val subscriptionService = SubscriptionService(subscriptionRepository)

    val user = UserFixture.generate()
    val now = LocalDateTime.now()

    given("구독을 취소한다.") {
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

            val deleteRequest = SubscriptionDto.DeleteRequest(userId = user.id!!, subscriptionId = subscriptionMock.id!!)

            then("구독 취소가 완료된다.") {
                subscriptionService.deleteSubscription(deleteRequest)
                verify(exactly = 1) { subscriptionRepository.deleteById(subscriptionMock.id!!) }
            }
        }
    }
})
