package com.example.subpay.domain

import java.time.LocalDateTime

class User {
    val id: Long = 0
    val name: String = ""
    val email: String = ""
    val password: String = ""
    val createdAt: LocalDateTime = LocalDateTime.now()
    val updatedAt: LocalDateTime = LocalDateTime.now()
}