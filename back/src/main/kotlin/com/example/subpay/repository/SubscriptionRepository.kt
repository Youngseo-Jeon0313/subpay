package com.example.subpay.repository

import com.example.subpay.domain.Subscription
import org.springframework.data.jpa.repository.JpaRepository

interface SubscriptionRepository: JpaRepository<Subscription, Long> {
}