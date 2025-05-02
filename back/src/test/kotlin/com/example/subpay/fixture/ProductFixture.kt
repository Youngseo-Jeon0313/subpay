package com.example.subpay.fixture

import com.example.subpay.domain.dto.ProductDto

object ProductFixture {
    fun generate(
        id: Long,
        name: String,
        price: Long,
        description: String,
        imageUrl: String,
        stockCount: Int,
        createdAt: String,
        updatedAt: String
    ): ProductDto.Response {
        return ProductDto.Response(
            id = id,
            name = name,
            price = price,
            description = description,
            imageUrl = imageUrl,
            stockCount = stockCount,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}