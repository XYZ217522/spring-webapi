package com.example.demo.auth

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

// @JvmOverloads 註解來產生多個建構函數，確保 Jackson 可以正確地反序列化物件
data class RegisterRequest @JvmOverloads constructor(
    @field:NotBlank @JsonProperty("firstname") val firstname: String,
    @field:NotBlank @JsonProperty("lastname") val lastname: String,
    @field:Email @JsonProperty("email") val email: String,
    @field:NotBlank @JsonProperty("password") val password: String,
)
