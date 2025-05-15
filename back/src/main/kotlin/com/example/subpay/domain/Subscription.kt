package com.example.subpay.domain

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "subscription")
data class Subscription(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val userId: Long? = null,
    val productId: Long? = null,
    var subscriptionDate: LocalDateTime = LocalDateTime.now(), // 구독 시작일
    var subscriptionExpirationDate: LocalDateTime = LocalDateTime.now(), // 구독 만료일

    @Enumerated(EnumType.STRING)
    var subscriptionStatus: SubscriptionStatus = SubscriptionStatus.INACTIVE,

    @Enumerated(EnumType.STRING)
    var subscriptionCycleType: SubscriptionCycleType? = null, // 구독 주기

    // JSON으로 저장: 매주 -> ["MONDAY", "WEDNESDAY"], 매월 -> ["1", "15"], 매년 -> ["03-01", "12-25"]
    @Column(columnDefinition = "json")
    var cycleDetails: String? = null // JSON 문자열

) {
    fun validate() {
        require(
            (subscriptionCycleType == SubscriptionCycleType.DAILY && cycleDetails == null) ||
                    (subscriptionCycleType != SubscriptionCycleType.DAILY && cycleDetails != null)
        ) {
            "주기 설정이 DAILY이면 cycleDetails는 null이어야 하고, 그 외엔 필수입니다."
        }

        when (subscriptionCycleType) {
            SubscriptionCycleType.YEARLY -> require(subscriptionExpirationDate.isAfter(subscriptionDate.plusYears(1))) {
                "연간 구독은 시작일로부터 최소 1년 이후 만료되어야 합니다."
            }
            SubscriptionCycleType.MONTHLY -> require(subscriptionExpirationDate.isAfter(subscriptionDate.plusMonths(1))) {
                "월간 구독은 시작일로부터 최소 1개월 이후 만료되어야 합니다."
            }
            SubscriptionCycleType.WEEKLY -> require(subscriptionExpirationDate.isAfter(subscriptionDate.plusWeeks(1))) {
                "주간 구독은 시작일로부터 최소 1주일 이후 만료되어야 합니다."
            }
            SubscriptionCycleType.BIWEEKLY -> require(subscriptionExpirationDate.isAfter(subscriptionDate.plusWeeks(2))) {
                "격주 구독은 시작일로부터 최소 2주일 이후 만료되어야 합니다."
            }
            SubscriptionCycleType.DAILY -> require(subscriptionExpirationDate.isAfter(subscriptionDate.plusDays(1))) {
                "일간 구독은 시작일로부터 최소 1일 이후 만료되어야 합니다."
            }
            else -> {}
        }
    }
    enum class SubscriptionStatus {
        ACTIVE,
        INACTIVE
    }
    enum class SubscriptionCycleType {
        YEARLY, // 매년 특정 월/일 (최대 10개)
        MONTHLY, // 매달 특정 일 (최대 5개)
        WEEKLY, // 매주 특정 요일 (최대 6개)
        BIWEEKLY, // 격주 특정 요일 (최대 6개)
        DAILY // 매일
    }
    enum class DayOfWeekEnum {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
    enum class MonthEnum {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
        JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    } // TODO : 윤달 고려


    fun isTodayInCycle(today: LocalDate): Boolean {
        val mapper = jacksonObjectMapper()

        if (subscriptionCycleType == SubscriptionCycleType.DAILY) {
            return true
        }

        return when (subscriptionCycleType) {
            SubscriptionCycleType.WEEKLY, SubscriptionCycleType.BIWEEKLY -> {
                // ex: ["MONDAY", "WEDNESDAY"]
                val days: List<String> = mapper.readValue(cycleDetails ?: "[]")
                val todayDay = today.dayOfWeek.name // "MONDAY" 등
                days.contains(todayDay)
            }
            SubscriptionCycleType.MONTHLY -> {
                // ex: ["1", "15"]
                val days: List<String> = mapper.readValue(cycleDetails ?: "[]")
                val todayDay = today.dayOfMonth.toString()
                days.contains(todayDay)
            }
            SubscriptionCycleType.YEARLY -> {
                // ex: ["03-01", "12-25"] (MM-dd 형식)
                val dates: List<String> = mapper.readValue(cycleDetails ?: "[]")
                val todayStr = today.format(java.time.format.DateTimeFormatter.ofPattern("MM-dd"))
                dates.contains(todayStr)
            }
            else -> false
        }
    }
}
