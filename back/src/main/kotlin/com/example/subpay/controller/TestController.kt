package com.example.subpay.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/test")
class TestController {
    @GetMapping()
    fun test(): ResponseEntity<String>  {// Changed to ResponseEntity<String> to match the return type{
        return ResponseEntity.ok("Test API is working");
    }
}