package com.example.demo.handler

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.HandlerExceptionResolver
import toJSONStr
import javax.persistence.EntityNotFoundException

@RestControllerAdvice
class GlobalExceptionHandler(@Qualifier("handlerExceptionResolver") private val handlerExceptionResolver: HandlerExceptionResolver) {

    @ExceptionHandler(ResponseStatusException::class)
    fun handleException(ex: ResponseStatusException): ResponseEntity<String> {
        return ResponseEntity(ex.message, ex.status)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleException(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity
            .badRequest()
            .body(mutableMapOf("status" to (ex.message ?: "unknown error")).toJSONStr())
    }

    @ExceptionHandler(EntityNotFoundException::class, NoSuchElementException::class)
    fun handleException(ex: Exception): ResponseEntity<String> {
        val message = ex.message ?: "not found"
        return ResponseEntity(mutableMapOf("status" to message).toJSONStr(), HttpStatus.BAD_REQUEST)
    }

}