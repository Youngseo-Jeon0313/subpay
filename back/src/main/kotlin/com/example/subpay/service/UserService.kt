package com.example.subpay.service

import com.example.subpay.domain.dto.UserDto
import com.example.subpay.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
   private val userRepository: UserRepository
) {

    fun getUserById(userId: Long): UserDto.Response {
        val user = userRepository.findById(userId).orElseThrow {
            throw RuntimeException("User not found")
        }
        return UserDto.Response(
            id = user.id!!,
            name = user.name,
            email = user.email,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
    }
}