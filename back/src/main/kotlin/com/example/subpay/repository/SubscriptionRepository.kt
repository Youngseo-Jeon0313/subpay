package com.example.subpay.repository

import com.example.subpay.domain.Subscription
import org.springframework.data.jpa.repository.JpaRepository

interface SubscriptionRepository: JpaRepository<Subscription, Long> {

    fun findByUserId(userId: Long): List<Subscription>

    fun findByUserIdAndProductId(userId: Long, productId: Long): Subscription?
}