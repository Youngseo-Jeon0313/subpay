package com.example.subpay.service

import com.example.subpay.domain.Card
import com.example.subpay.domain.dto.CardDto
import com.example.subpay.repository.CardRepository
import org.springframework.stereotype.Service

@Service
class CardService (
    private val cardRepository: CardRepository,
){

    fun registerCard(cardDto: CardDto.Request) : CardDto.Response {
        val newCard = Card(
            userId = cardDto.userId,
            cardNumber = cardDto.cardNumber,
            expirationDate = cardDto.expirationDate,
            cvv = cardDto.cvv,
            balance = cardDto.balance
        )
        val cardCount = cardRepository.countByUserId(cardDto.userId)
        if (cardCount >= 3) {
            throw IllegalArgumentException("결제수단은 최대 3개까지 등록할 수 있습니다.")
        }
        val card = cardRepository.save(newCard)
        return CardDto.Response(
            id = card.id!!,
            userId = card.userId,
            cardNumber = card.cardNumber,
            expirationDate = card.expirationDate,
            cvv = card.cvv,
            balance = card.balance,
            priority = card.priority
        )
    }

    fun deleteCard(cardId: Long) {

    }
}