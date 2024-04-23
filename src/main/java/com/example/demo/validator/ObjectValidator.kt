package com.example.demo.validator

import org.springframework.stereotype.Component
import java.util.stream.Collectors
import javax.validation.Validation

@Component
class ObjectValidator<T> {

    private val factory = Validation.buildDefaultValidatorFactory()
    private val validator = factory.validator

    fun validate(objectToValidator: T) {
        val violations = validator.validate(objectToValidator)
        println("violations = $violations")
        if (violations.isNotEmpty()) {
            violations
                .stream()
                .map { it.message }
                .collect(Collectors.toSet())
                .run { throw IllegalArgumentException(this.toString()) }
        }
    }
}