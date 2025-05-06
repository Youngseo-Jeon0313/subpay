package com.example.subpay.controller

import com.example.subpay.domain.dto.CardDto
import com.example.subpay.service.CardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/card")
class CardController(
    private val cardService: CardService
) {

    @RequestMapping("/register")
    fun registerCard(cardDto: CardDto.Request): ResponseEntity<CardDto.Response> {
        val response = cardService.registerCard(cardDto)
        return ResponseEntity.ok(response)
    }

    @RequestMapping("/delete")
    fun deleteCard(cardId: Long): ResponseEntity<List<CardDto.Response>> {
        val response = cardService.deleteCard(cardId)
        return ResponseEntity.ok(response)
    }

    // 카드들을 전부 가져온 후 List에 따라 순서 바꿈
    @PatchMapping("/update/priority")
    fun updateCardPriority(cardPriorityUpdateRequest: CardDto.CardPriorityUpdateRequest): ResponseEntity<List<CardDto.Response>> {
        val response = cardService.updateCardPriority(cardPriorityUpdateRequest)
        return ResponseEntity.ok(response)
    }
}
