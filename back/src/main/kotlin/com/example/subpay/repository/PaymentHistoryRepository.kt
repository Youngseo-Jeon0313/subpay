package com.example.subpay.repository

import com.example.subpay.domain.PaymentHistory
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentHistoryRepository : JpaRepository<PaymentHistory, Long>
