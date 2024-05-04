package com.example.demo.controller

import com.example.demo.auth.AuthenticationRequest
import com.example.demo.auth.AuthenticationResponse
import com.example.demo.auth.RegisterRequest
import com.example.demo.service.AuthenticationService
import io.reactivex.rxjava3.core.Single
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auth")
class AuthenticationController(
    private val authService: AuthenticationService
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<Single<AuthenticationResponse>> {
        val registerSingle = authService.register(request)
        return ResponseEntity.ok(registerSingle)
    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody request: AuthenticationRequest): ResponseEntity<Single<AuthenticationResponse>> {
        val authenticateSingle = authService.authenticate(request)
        return ResponseEntity.ok(authenticateSingle)
    }


}