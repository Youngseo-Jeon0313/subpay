package com.example.subpay.controller

import com.example.subpay.domain.dto.UserDto
import com.example.subpay.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/details")
    fun getUserDetails(userId: Long): ResponseEntity<UserDto.Response> {
        val userDetails = userService.getUserById(userId)
        return ResponseEntity.ok(userDetails)
    }
}