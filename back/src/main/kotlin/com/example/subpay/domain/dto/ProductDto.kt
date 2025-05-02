package com.example.subpay.domain.dto

class ProductDto {

    data class Request (
        val name: String,
        val price: Long,
        val description: String,
        val imageUrl: String,
    )

    data class Response (
        val id: Long,
        val name: String,
        val description: String,
        val price: Long,
        val imageUrl: String,
        val stockCount: Int,
        val createdAt: String,
        val updatedAt: String
    )
}