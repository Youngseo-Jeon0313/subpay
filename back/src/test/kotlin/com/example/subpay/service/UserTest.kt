package com.example.subpay.service

import com.example.subpay.fixture.UserFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class UserTest : BehaviorSpec({
    Given("UserDto.Response를 생성할 때") {
        val now = LocalDateTime.now()

        When("UserFixture.generate를 사용하면") {
            val user = UserFixture.generate(
                id = 1L,
                name = "유저1",
                email = "test@gmail.com",
                password = "password1234",
                createdAt = now,
                updatedAt = now
            )

            Then("필드 값들이 정확히 매핑되어야 한다") {
                user.id shouldBe 1L
                user.name shouldBe "유저1"
                user.email shouldBe "test@gmail.com"
                user.password shouldBe "password1234"
                user.createdAt shouldBe now
                user.updatedAt shouldBe now
            }
        }
    }
})