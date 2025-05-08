package com.example.subpay.domain

import jakarta.persistence.*
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
    init {
        require(
            (subscriptionCycleType == SubscriptionCycleType.DAILY && cycleDetails == null) ||
                (subscriptionCycleType != SubscriptionCycleType.DAILY && cycleDetails != null)
        ) {
            "주기 설정이 DAILY이면 cycleDetails는 null이어야 하고, DAILY가 아니면 반드시 있어야 합니다."
        }

        when (subscriptionCycleType) {
            SubscriptionCycleType.YEARLY -> require(subscriptionExpirationDate.isAfter(subscriptionDate.plusYears(1))) {
                "구독상품이 연간 상품일 경우, 구독 만료일은 구독 시작일로부터 1년 이후여야 합니다."
            }
            SubscriptionCycleType.MONTHLY -> require(subscriptionExpirationDate.isAfter(subscriptionDate.plusMonths(1))) {
                "구독상품이 월간 상품일 경우, 구독 만료일은 구독 시작일로부터 1개월 이후여야 합니다."
            }
            SubscriptionCycleType.WEEKLY -> require(subscriptionExpirationDate.isAfter(subscriptionDate.plusWeeks(1))) {
                "구독상품이 주간 상품일 경우, 구독 만료일은 구독 시작일로부터 1주일 이후여야 합니다."
            }
            SubscriptionCycleType.BIWEEKLY -> require(subscriptionExpirationDate.isAfter(subscriptionDate.plusWeeks(2))) {
                "구독상품이 격주 상품일 경우, 구독 만료일은 구독 시작일로부터 2주일 이후여야 합니다."
            }
            SubscriptionCycleType.DAILY -> require(subscriptionExpirationDate.isAfter(subscriptionDate.plusDays(1))) {
                "구독상품이 일간 상품일 경우, 구독 만료일은 구독 시작일로부터 1일 이후여야 합니다."
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
}
