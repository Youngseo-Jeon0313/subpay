package com.example.subpay.fixture

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.subpay.domain.User
import java.time.LocalDateTime
import kotlin.random.Random

object UserFixture {
    val fixture = kotlinFixture()

    fun generate(
        id: Long? = null,
        name: String? = null,
        email: String? = null,
        password: String? = null,
        createdAt: LocalDateTime? = null,
        updatedAt: LocalDateTime? = null
    ): User = fixture<User> {
        // 정의하지 않은 필드는 랜덤 데이터를 생성
        property(User::id) { id ?: Random.nextLong(1, 10000) }
        property(User::name) { name ?: fixture<String>() }
        property(User::email) { email ?: fixture<String>() }
        property(User::password) { password ?: fixture<String>() }
        property(User::createdAt) { createdAt ?: LocalDateTime.now() }
        property(User::updatedAt) { updatedAt ?: LocalDateTime.now() }
    }
}
