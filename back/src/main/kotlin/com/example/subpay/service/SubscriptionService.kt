package com.example.subpay.service

import com.example.subpay.domain.Subscription
import com.example.subpay.domain.dto.SubscriptionDto
import com.example.subpay.repository.SubscriptionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SubscriptionService(
    private val subscriptionRepository: SubscriptionRepository
) {
    @Transactional
    fun createSubscription(request: SubscriptionDto.CreateRequest) {
        val subscription = Subscription(
            userId = request.userId,
            productId = request.productId,
            subscriptionDate = request.subscriptionDate,
            subscriptionExpirationDate = request.subscriptionExpirationDate,
            subscriptionStatus = Subscription.SubscriptionStatus.INACTIVE,
            subscriptionCycleType = Subscription.SubscriptionCycleType.DAILY,
            cycleDetails = null
        )
        // 이미 구독하고 있는 상품인지 확인
        val existingSubscription = subscriptionRepository.findByUserIdAndProductId(request.userId, request.productId)
        if (existingSubscription != null) {
            throw IllegalArgumentException("이미 구독하고 있는 상품입니다.")
        }
        subscriptionRepository.save(subscription)
    }

    @Transactional
    fun deleteSubscription(request: SubscriptionDto.DeleteRequest): List<SubscriptionDto.Response>{
        subscriptionRepository.deleteById(request.subscriptionId)
        val subscriptionResponse = subscriptionRepository.findByUserId(request.userId)
        return subscriptionResponse.map {
            SubscriptionDto.Response(
                productId = it.productId!!,
                subscriptionDate = it.subscriptionDate!!,
                subscriptionExpirationDate = it.subscriptionExpirationDate!!,
                subscriptionStatus = it.subscriptionStatus.name,
                subscriptionCycleType = it.subscriptionCycleType!!.name,
            )
        }
    }
}
