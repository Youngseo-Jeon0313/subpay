package com.example.subpay.service

import com.example.subpay.domain.Card
import com.example.subpay.domain.dto.CardDto
import com.example.subpay.repository.CardRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CardService (
    private val cardRepository: CardRepository,
){

    @Transactional
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

    @Transactional
    fun deleteCard(cardId: Long): List<CardDto.Response> {
        val card = cardRepository.findById(cardId).orElseThrow {
            IllegalArgumentException("해당 결제수단이 존재하지 않습니다.")
        }
        cardRepository.delete(card)
        val cards = cardRepository.findByUserId(card.userId)
        return cards.map {
            CardDto.Response(
                id = it.id!!,
                userId = it.userId,
                cardNumber = it.cardNumber,
                expirationDate = it.expirationDate,
                cvv = it.cvv,
                balance = it.balance,
                priority = it.priority
            )
        }
    }

    @Transactional
    fun updateCardPriority(cardPriorityUpdateRequest: CardDto.CardPriorityUpdateRequest): List<CardDto.Response> {
        val userId = cardPriorityUpdateRequest.userId
        val cardIds = cardPriorityUpdateRequest.cardIds.toSet() // set으로 중복 제거

        val existingCards = cardRepository.findByUserId(userId)

        val existingCardIds = existingCards.map { it.id }.toSet()
        if (existingCardIds != cardIds) {
            throw IllegalArgumentException("기존 카드의 개수와 ID가 일치하지 않습니다.")
        }

        // 카드를 새로 받은 순서대로 우선순위 업데이트
        val cardsToUpdate = existingCards.mapIndexed { index, card ->
            val newPriority = cardIds.indexOf(card.id) + 1
            card.updatePriority(newPriority)
            card
        }
        cardRepository.saveAll(cardsToUpdate)

        return cardsToUpdate.map {
            CardDto.Response(
                id = it.id!!,
                userId = it.userId,
                cardNumber = it.cardNumber,
                expirationDate = it.expirationDate,
                cvv = it.cvv,
                balance = it.balance,
                priority = it.priority
            )
        }
    }
}