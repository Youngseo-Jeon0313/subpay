package com.example.subpay.fixture

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.subpay.domain.Subscription
import java.time.LocalDateTime

object SubscriptionFixture {
    private val fixture = kotlinFixture()

    fun generate(
        id: Long? = null,
        userId: Long? = null,
        productId: Long? = null,
        subscriptionDate: LocalDateTime? = null,
        subscriptionExpirationDate: LocalDateTime? = null,
        subscriptionStatus: Subscription.SubscriptionStatus = Subscription.SubscriptionStatus.INACTIVE,
        subscriptionCycleType: Subscription.SubscriptionCycleType? = Subscription.SubscriptionCycleType.DAILY,
        cycleDetails: String? = null
    ): Subscription {
        val finalSubscriptionDate = subscriptionDate ?: LocalDateTime.now()
        val finalSubscriptionExpirationDate = subscriptionExpirationDate ?: finalSubscriptionDate.plusMonths(1)

        val subscriptionCycleDetails = when (subscriptionCycleType) {
            Subscription.SubscriptionCycleType.WEEKLY -> "[\"MONDAY\", \"FRIDAY\"]"
            Subscription.SubscriptionCycleType.MONTHLY -> "[\"1\", \"15\"]"
            Subscription.SubscriptionCycleType.YEARLY -> "[\"03-01\", \"12-25\"]"
            Subscription.SubscriptionCycleType.BIWEEKLY -> "[\"TUESDAY\"]"
            else -> null
        }

        return Subscription(
            id = id ?: fixture<Long>(),
            userId = userId ?: fixture<Long>(),
            productId = productId ?: fixture<Long>(),
            subscriptionDate = finalSubscriptionDate,
            subscriptionExpirationDate = finalSubscriptionExpirationDate,
            subscriptionStatus = subscriptionStatus,
            subscriptionCycleType = subscriptionCycleType,
            cycleDetails = cycleDetails ?: subscriptionCycleDetails
        )
    }
}
