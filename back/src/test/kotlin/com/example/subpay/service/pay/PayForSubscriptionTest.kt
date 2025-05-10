package com.example.subpay.service.pay

import com.example.subpay.domain.PaymentHistory
import com.example.subpay.domain.Subscription
import com.example.subpay.fixture.ProductFixture
import com.example.subpay.fixture.SubscriptionFixture
import com.example.subpay.fixture.UserFixture
import com.example.subpay.repository.PaymentHistoryRepository
import com.example.subpay.repository.SubscriptionRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.*

class PayForSubscriptionTest : BehaviorSpec({

    val subscriptionRepository = mockk<SubscriptionRepository>()
    val paymentHistoryRepository = mockk<PaymentHistoryRepository>()

    val user = UserFixture.generate()
    val product = ProductFixture.generate(price = 10000L)
    val now = LocalDateTime.now()

    given("구독 결제를 진행한다") {
        val subscription = SubscriptionFixture.generate(
            userId = user.id,
            productId = product.id,
            subscriptionDate = now,
            subscriptionExpirationDate = now.plusMonths(2),
            subscriptionStatus = Subscription.SubscriptionStatus.ACTIVE,
            subscriptionCycleType = Subscription.SubscriptionCycleType.MONTHLY,
            cycleDetails = """["1", "15"]"""
        )

        `when`("유효한 구독 정보로 결제를 시도하는 경우") {
            every { subscriptionRepository.findById(any()) } returns Optional.of(subscription)
            every { paymentHistoryRepository.save(any()) } returns PaymentHistory(
                subscriptionId = subscription.id,
                userId = subscription.userId,
                amount = product.price,
                paymentStatus = PaymentHistory.PaymentStatus.SUCCESS,
                paymentDate = now
            )
            every { subscriptionRepository.save(any()) } returns subscription

            then("결제가 성공적으로 완료된다") {
                // TODO: springbatch 코드 연동하여 검증
            }
        }
    }
})
