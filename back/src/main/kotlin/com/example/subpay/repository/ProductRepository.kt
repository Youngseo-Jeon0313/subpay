package com.example.subpay.repository

import com.example.subpay.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>
