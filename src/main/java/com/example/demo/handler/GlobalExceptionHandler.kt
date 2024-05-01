package com.example.demo.handler

import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.HandlerExceptionResolver
import toJSONStr
import java.util.function.Consumer


@RestControllerAdvice
class GlobalExceptionHandler(@Qualifier("handlerExceptionResolver") private val handlerExceptionResolver: HandlerExceptionResolver) {

    @ExceptionHandler(ResponseStatusException::class)
    fun handleException(ex: ResponseStatusException): ResponseEntity<String> {
        return ResponseEntity(ex.message, ex.statusCode)
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): Map<String, String> {
        val errors: MutableMap<String, String> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage() ?: "unknown error"
            errors[fieldName] = errorMessage
        })
        return errors
    }

}