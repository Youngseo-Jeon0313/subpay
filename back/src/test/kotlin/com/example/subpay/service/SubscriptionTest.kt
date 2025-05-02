package com.example.subpay.service

import io.kotest.core.spec.style.BehaviorSpec

class SubscriptionTest : BehaviorSpec({

    given("구독을 3개월로 신청한다.") {
        `when`("해당 상품이 존재하지 않는다면") {
            then("해당 상품 구독을 실패한다.") {

            }
        }

        `when`("해당 상품이 존재한다면") {
            then("구독 신청이 완료된다.") {

            }
        }
    }

    given("구독 상품을 조회한다.") {
        `when`("기존에 존재하는 구독 상품을 조회한다면") {
            then("구독 상품이 정상적으로 조회된다.") {

            }
        }
    }

    given("구독을 취소한다.") {
        `when`("구독이 존재하지 않는다면") {
            then("구독 취소가 실패한다.") {

            }
        }
        `when`("구독이 존재한다면") {
            then("구독 취소가 완료된다.") {

            }
        }
    }
})