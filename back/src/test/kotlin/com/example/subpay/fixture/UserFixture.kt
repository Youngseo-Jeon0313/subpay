package com.example.subpay.fixture

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.subpay.domain.dto.UserDto
import java.time.LocalDateTime

object UserFixture {
    val fixture = kotlinFixture()

    fun generate(
        id: Long? = null,
        name: String? = null,
        email: String? = null,
        password: String? = null,
        createdAt: LocalDateTime? = null,
        updatedAt: LocalDateTime? = null,
    ): UserDto.Response = fixture<UserDto.Response> {
        // 정의하지 않은 필드는 랜덤 데이터를 생성
        id?.let { property(UserDto.Response::id) { it } }
        name?.let { property(UserDto.Response::name) { it } }
        email?.let { property(UserDto.Response::email) { it } }
        password?.let { property(UserDto.Response::password) { it } }
        createdAt?.let { property(UserDto.Response::createdAt) { it } }
        updatedAt?.let { property(UserDto.Response::updatedAt) { it } }
    }
}
