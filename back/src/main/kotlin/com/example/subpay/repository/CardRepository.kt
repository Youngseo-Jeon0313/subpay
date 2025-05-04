package com.example.subpay.repository

import com.example.subpay.domain.Card
import org.springframework.data.jpa.repository.JpaRepository

interface CardRepository: JpaRepository<Card, Long> {

    fun countByUserId(userId: Long): Int
    fun findByUserId(userId: Long): List<Card>
}