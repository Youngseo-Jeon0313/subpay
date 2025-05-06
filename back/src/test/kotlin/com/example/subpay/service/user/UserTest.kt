package com.example.subpay.service.user

import com.example.subpay.domain.dto.UserDto
import com.example.subpay.fixture.UserFixture
import com.example.subpay.repository.UserRepository
import com.example.subpay.service.UserService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.lang.RuntimeException
import java.time.LocalDateTime

class UserTest : BehaviorSpec({
    val userService = mockk<UserService>()
    val userRepository = mockk<UserRepository>()

    Given("User를 생성할 때") {
        When("User 객체를 생성하면") {
            val now = LocalDateTime.now()
            val user = UserFixture.generate(
                id = 1L,
                name = "유저1",
                email = "test@gmail.com",
                password = "password",
                createdAt = now,
                updatedAt = now
            )
            Then("필드 값들이 정확히 매핑되어야 한다") {
                user.id shouldBe 1L
                user.name shouldBe "유저1"
                user.email shouldBe "test@gmail.com"
                user.password shouldBe "password"
                user.createdAt shouldBe now
                user.updatedAt shouldBe now
            }
        }
    }

    Given("User 정보를 가져올 때") {
        When("User가 없으면") {
            Then("RuntimeException을 던진다.") {
                shouldThrow<RuntimeException> {
                    userService.getUserById(1L)
                }
            }
        }

        When("User가 있으면") {
            val now = LocalDateTime.now()
            val user = UserFixture.generate(
                id = 1L,
                name = "유저1",
                email = "test@gmail.com",
                password = "password",
                createdAt = now,
                updatedAt = now
            )

            every { userRepository.findById(1L) } returns java.util.Optional.of(user)
            every { userService.getUserById(1L) } returns UserDto.Response(
                id = user.id!!,
                name = user.name,
                email = user.email,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt
            )

            Then("해당 유저의 DTO를 가져온다.") {
                userService.getUserById(1L).name shouldBe "유저1"
            }
        }
    }
})
