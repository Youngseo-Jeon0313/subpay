package com.example.subpay.fixture

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.subpay.domain.User
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
    ): User = fixture<User> {
        // 정의하지 않은 필드는 랜덤 데이터를 생성
        id?.let { property(User::id) { it } }
        name?.let { property(User::name) { it } }
        email?.let { property(User::email) { it } }
        password?.let { property(User::password) { it } }
        createdAt?.let { property(User::createdAt) { it } }
        updatedAt?.let { property(User::updatedAt) { it } }
    }
}
