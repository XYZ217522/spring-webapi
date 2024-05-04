package com.example.demo.handler

import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.HandlerExceptionResolver


@RestControllerAdvice
class GlobalExceptionHandler(@Qualifier("handlerExceptionResolver") private val handlerExceptionResolver: HandlerExceptionResolver) {

    @ExceptionHandler(ResponseStatusException::class)
    fun handleException(ex: ResponseStatusException): ResponseEntity<String> {
        return ResponseEntity(ex.message, ex.statusCode)
    }

    @ExceptionHandler(
        IllegalArgumentException::class,
        EntityNotFoundException::class,
        NoSuchElementException::class,
        UsernameNotFoundException::class
    )
    fun handleException(ex: IllegalArgumentException): ResponseEntity<Map<String, String>> {
        return ResponseEntity
            .badRequest()
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapOf("status" to (ex.message ?: "unknown error")))
    }
}