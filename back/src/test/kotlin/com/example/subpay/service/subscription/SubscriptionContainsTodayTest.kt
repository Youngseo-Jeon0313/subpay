package com.example.subpay.service.subscription

import com.example.subpay.domain.Subscription
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern

class SubscriptionContainsTodayTest : BehaviorSpec({

    val today = LocalDate.of(2025, 5, 15) // 고정 날짜
    val todayDayOfWeek = today.dayOfWeek.name          // "THURSDAY"
    val todayDayOfMonth = today.dayOfMonth.toString()  // "15"
    val todayMonthDay = today.format(ofPattern("MM-dd")) // "05-15"
    val mapper = jacksonObjectMapper()

    given("구독 주기가 DAILY일 때") {
        val subscription = Subscription(
            subscriptionCycleType = Subscription.SubscriptionCycleType.DAILY,
            cycleDetails = null
        )
        `when`("언제든지 호출하면") {
            then("항상 true를 반환한다") {
                subscription.isTodayInCycle() shouldBe true
            }
        }
    }

    given("구독 주기가 WEEKLY일 때") {
        `when`("cycleDetails에 오늘 요일이 포함되어 있다면") {
            val subscription = Subscription(
                subscriptionCycleType = Subscription.SubscriptionCycleType.WEEKLY,
                cycleDetails = mapper.writeValueAsString(listOf("THURSDAY", "FRIDAY"))
            )
            then("true를 반환한다") {
                subscription.isTodayInCycle() shouldBe true
            }
        }
    }

    given("구독 주기가 MONTHLY일 때") {
        `when`("cycleDetails에 오늘 일이 포함되어 있다면") {
            val subscription = Subscription(
                subscriptionCycleType = Subscription.SubscriptionCycleType.MONTHLY,
                cycleDetails = mapper.writeValueAsString(listOf("15", "23"))
            )
            then("true를 반환한다") {
                subscription.isTodayInCycle() shouldBe true
            }
        }
    }

    given("구독 주기가 YEARLY일 때") {
        `when`("cycleDetails에 오늘 날짜가 MM-dd 형식으로 포함되어 있다면") {
            val subscription = Subscription(
                subscriptionCycleType = Subscription.SubscriptionCycleType.YEARLY,
                cycleDetails = mapper.writeValueAsString(listOf("05-15", "08-20", "12-25"))
            )
            then("true를 반환한다") {
                subscription.isTodayInCycle() shouldBe true
            }
        }
    }

    given("구독 주기가 WEEKLY인데 오늘 요일이 아닌 경우") {
        `when`("cycleDetails에 오늘이 빠져있다면") {
            val subscription = Subscription(
                subscriptionCycleType = Subscription.SubscriptionCycleType.WEEKLY,
                cycleDetails = mapper.writeValueAsString(listOf("MONDAY", "FRIDAY"))
            )
            then("false를 반환한다") {
                subscription.isTodayInCycle() shouldBe false
            }
        }
    }

    given("구독 주기가 MONTHLY인데 오늘 날짜가 빠져 있는 경우") {
        `when`("cycleDetails에 오늘 일이 없으면") {
            val subscription = Subscription(
                subscriptionCycleType = Subscription.SubscriptionCycleType.MONTHLY,
                cycleDetails = mapper.writeValueAsString(listOf("1", "2", "3"))
            )
            then("false를 반환한다") {
                subscription.isTodayInCycle() shouldBe false
            }
        }
    }
})
