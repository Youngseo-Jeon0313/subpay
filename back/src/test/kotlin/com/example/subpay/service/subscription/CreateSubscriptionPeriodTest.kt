package com.example.subpay.service.subscription

import com.example.subpay.domain.Subscription
import com.example.subpay.fixture.ProductFixture
import com.example.subpay.fixture.SubscriptionFixture
import com.example.subpay.fixture.UserFixture
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class CreateSubscriptionPeriodTest : BehaviorSpec({

    given("오늘부터 구독을 신청한다.") {

        val user = UserFixture.generate()
        val now = LocalDateTime.now()

        `when`("주기는 하루로 설정하고, 마지막 구독일은 오늘로부터 2달 후이다.") {
            val product = ProductFixture.generate()
            val subscriptionMock = SubscriptionFixture.generate(
                userId = user.id,
                productId = product.id,
                subscriptionDate = now,
                subscriptionExpirationDate = now.plusMonths(2),
                subscriptionStatus = Subscription.SubscriptionStatus.INACTIVE,
                subscriptionCycleType = Subscription.SubscriptionCycleType.DAILY,
                cycleDetails = null
            )
            then("구독 주기는 하루로 설정되며, 구독 만료일은 오늘로부터 3달 후로 설정된다.") {
                subscriptionMock.subscriptionCycleType shouldBe Subscription.SubscriptionCycleType.DAILY
                subscriptionMock.subscriptionExpirationDate shouldBe now.plusMonths(2)
            }
        }

        `when`("주기는 1주로 설정하고, 마지막 구독일은 오늘로부터 3달 후이다. 화요일과 금요일에 구독한다.") {
            val product = ProductFixture.generate()
            val subscriptionMock = SubscriptionFixture.generate(
                userId = user.id,
                productId = product.id,
                subscriptionDate = now,
                subscriptionExpirationDate = now.plusMonths(1),
                subscriptionStatus = Subscription.SubscriptionStatus.INACTIVE,
                subscriptionCycleType = Subscription.SubscriptionCycleType.WEEKLY,
                cycleDetails = """["TUESDAY", "FRIDAY"]"""
            )
            then("구독 주기는 1주 설정되며, 구독 만료일은 오늘로부터 1달 후로 설정된다.") {
                subscriptionMock.subscriptionCycleType shouldBe Subscription.SubscriptionCycleType.WEEKLY
                subscriptionMock.subscriptionExpirationDate shouldBe now.plusMonths(1)
            }
        }

        `when`("주기는 1달으로 설정하고, 마지막 구독일은 오늘로부터 3달 후이다. 1일과 15일에 구독한다.") {
            val product = ProductFixture.generate()
            val subscriptionMock = SubscriptionFixture.generate(
                userId = user.id,
                productId = product.id,
                subscriptionDate = now,
                subscriptionExpirationDate = now.plusMonths(3),
                subscriptionStatus = Subscription.SubscriptionStatus.INACTIVE,
                subscriptionCycleType = Subscription.SubscriptionCycleType.MONTHLY,
                cycleDetails = """["1", "15"]"""
            )
            then("구독 주기는 1달로 설정되며, 구독 만료일은 오늘로부터 3달 후로 설정된다.") {
                subscriptionMock.subscriptionCycleType shouldBe Subscription.SubscriptionCycleType.MONTHLY
                subscriptionMock.subscriptionExpirationDate shouldBe now.plusMonths(3)
            }
        }

        `when`("주기는 1년으로 설정하고, 마지막 구독일은 오늘로부터 1년 후이다. 3월 1일과 12월 25일에 구독한다.") {
            val product = ProductFixture.generate()
            val subscriptionMock = SubscriptionFixture.generate(
                userId = user.id,
                productId = product.id,
                subscriptionDate = now,
                subscriptionExpirationDate = now.plusYears(3),
                subscriptionStatus = Subscription.SubscriptionStatus.INACTIVE,
                subscriptionCycleType = Subscription.SubscriptionCycleType.YEARLY,
                cycleDetails = """["03-01", "12-25"]"""
            )
            then("구독 주기는 1년으로 설정되며, 구독 만료일은 오늘로부터 1년 후로 설정된다.") {
                subscriptionMock.subscriptionCycleType shouldBe Subscription.SubscriptionCycleType.YEARLY
                subscriptionMock.subscriptionExpirationDate shouldBe now.plusYears(3)
            }
        }
        `when`("주기는 1년으로 설정하고, 마지막 구독일은 오늘로부터 3개월 뒤이다.") {
            then("조건에 맞지 않기 때문에 예외가 발생한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    val product = ProductFixture.generate()
                    SubscriptionFixture.generate(
                        userId = user.id,
                        productId = product.id,
                        subscriptionDate = now,
                        subscriptionExpirationDate = now.plusMonths(3),
                        subscriptionStatus = Subscription.SubscriptionStatus.INACTIVE,
                        subscriptionCycleType = Subscription.SubscriptionCycleType.YEARLY,
                        cycleDetails = """["03-01", "12-25"]"""
                    )
                }
            }
        }
    }
})
