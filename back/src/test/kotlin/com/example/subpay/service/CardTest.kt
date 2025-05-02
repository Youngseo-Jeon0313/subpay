package com.example.subpay.service

import io.kotest.core.spec.style.BehaviorSpec

class CardTest : BehaviorSpec({
    given("사용자가 결제수단을 등록할 때") {

        `when`("카드 정보가 유효하면") {


            then("결제수단이 정상적으로 등록되어야 한다") {

            }
        }

        `when`("사용자가 이미 3개의 결제수단을 등록한 상태에서 또 등록하려고 하면") {

            then("새 결제수단 등록은 실패해야 한다") {

            }
        }

        `when`("새 결제수단을 기본으로 등록하려고 하면") {

            then("기존 기본 결제수단은 기본 아님으로 바뀌고 새 결제수단이 기본이 되어야 한다") {

            }
        }
    }
})