package com.example.subpay.fixture

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.subpay.domain.Subscription
import java.time.LocalDateTime

object SubscriptionFixture {
    val fixture = kotlinFixture()

    fun generate(
        id: Long? = null,
        userId: Long? = null,
        productId: Long? = null,
        subscriptionDate: LocalDateTime? = LocalDateTime.now(),
        subscriptionExpirationDate: LocalDateTime? = LocalDateTime.now(),
        subscriptionStatus: Subscription.SubscriptionStatus = Subscription.SubscriptionStatus.INACTIVE,
        subscriptionCycleType: Subscription.SubscriptionCycleType? = null,
        cycleDetails: String? = null
    ): Subscription = fixture<Subscription> {
        id?.let { property(Subscription::id) { it } }
        userId?.let { property(Subscription::userId) { it } }
        productId?.let { property(Subscription::productId) { it } }
        subscriptionDate?.let { property(Subscription::subscriptionDate) { it } }
        subscriptionExpirationDate?.let { property(Subscription::subscriptionExpirationDate) { it } }
        subscriptionStatus.let { property(Subscription::subscriptionStatus) { it } }
        subscriptionCycleType?.let { property(Subscription::subscriptionCycleType) { it } }
        cycleDetails?.let { property(Subscription::cycleDetails) { it } }
    }
}