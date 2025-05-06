package com.example.subpay.controller

import com.example.subpay.domain.dto.SubscriptionDto
import com.example.subpay.service.SubscriptionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/subscription")
class SubscriptionController(
    private val subscriptionService: SubscriptionService
) {

    @PostMapping("/create")
    fun createSubscription(request: SubscriptionDto.Request): ResponseEntity<String> {
        subscriptionService.createSubscription(request)
        return ResponseEntity.ok("구독 신청이 완료되었습니다")
    }
}
