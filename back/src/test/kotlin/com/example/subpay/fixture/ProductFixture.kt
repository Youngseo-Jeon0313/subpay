package com.example.subpay.fixture

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.subpay.domain.Product
import java.time.LocalDateTime

object ProductFixture {
    val fixture = kotlinFixture()
    fun generate(
        id: Long = 0,
        name: String = "",
        price: Long = 0,
        description: String = "",
        imageUrl: String = "",
        stockCount: Int = 0,
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now(),
    ): Product = fixture<Product> {
        property(Product::id) { id }
        property(Product::name) { name }
        property(Product::price) { price }
        property(Product::description) { description }
        property(Product::imageUrl) { imageUrl }
        property(Product::stockCount) { stockCount }
        property(Product::createdAt) { createdAt }
        property(Product::updatedAt) { updatedAt }
    }
}