package com.example.subpay.service

import com.example.subpay.domain.PaymentHistory
import com.example.subpay.domain.Subscription
import com.example.subpay.repository.CardRepository
import com.example.subpay.repository.PaymentHistoryRepository
import com.example.subpay.repository.ProductRepository
import com.example.subpay.repository.SubscriptionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PayService(
    private val subscriptionRepository: SubscriptionRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository,
    private val cardRepository: CardRepository,
    private val productRepository: ProductRepository,
) {

    @Transactional
    fun pay(subscription: Subscription): PaymentHistory {

        val cards = cardRepository.findByUserId(subscription.userId!!)
        val product = productRepository.findById(subscription.productId!!)
            .orElseThrow { throw RuntimeException("Product not found") }

        val usableCard = cards.firstOrNull { it.balance >= product.price }
            ?: throw RuntimeException("user ${subscription.userId}는 product ${subscription.productId}를 결제할 수 있는 카드가 없습니다.")

        if (product.stockCount <= 0) {
            throw RuntimeException("Product ${subscription.productId} 는 재고가 없습니다.")
        }

        usableCard.let { card ->
            card.balance -= product.price
            product.stockCount -= 1
            cardRepository.save(card)
        }

        val now = LocalDateTime.now();
        val paymentHistory = PaymentHistory(
            subscriptionId = subscription.id,
            userId = subscription.userId,
            amount = product.price,
            paymentStatus = PaymentHistory.PaymentStatus.SUCCESS,
            paymentDate = now,
        )

        val savedPaymentHistory = paymentHistoryRepository.save(paymentHistory)

        subscription.subscriptionStatus = Subscription.SubscriptionStatus.ACTIVE
        subscriptionRepository.save(subscription)

        return savedPaymentHistory
    }
}
