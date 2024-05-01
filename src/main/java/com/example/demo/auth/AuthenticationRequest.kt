package com.example.demo.auth

import com.fasterxml.jackson.annotation.JsonProperty

// @JvmOverloads 註解來產生多個建構函數，確保 Jackson 可以正確地反序列化物件
data class AuthenticationRequest @JvmOverloads constructor(
    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String
)
