package com.example.subpay.controller

import com.example.subpay.domain.dto.SubscriptionDto
import com.example.subpay.service.SubscriptionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/subscription")
class SubscriptionController(
    private val subscriptionService: SubscriptionService
) {

    @PostMapping
    fun createSubscription(request: SubscriptionDto.CreateRequest): ResponseEntity<String> {
        subscriptionService.createSubscription(request)
        return ResponseEntity.ok("구독 신청이 완료되었습니다")
    }

    @DeleteMapping
    fun deleteSubscription(request: SubscriptionDto.DeleteRequest): ResponseEntity<List<SubscriptionDto.Response>>{
        val subscriptionList = subscriptionService.deleteSubscription(request)
        return ResponseEntity.ok(subscriptionList)
    }
}
