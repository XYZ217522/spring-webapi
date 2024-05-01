package com.example.demo.service

import com.example.demo.auth.AuthenticationRequest
import com.example.demo.auth.AuthenticationResponse
import com.example.demo.auth.RegisterRequest
import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    fun register(request: RegisterRequest): Single<AuthenticationResponse> {
        return Single
            .fromCallable {
                User.Builder()
                    .firstname(request.firstname)
                    .lastname(request.lastname)
                    .email(request.email)
                    .password(passwordEncoder.encode(request.password))
                    .role(Role.USER)
                    .build()
            }
            .map { user -> userRepository.save(user) }
            .map { user -> jwtService.generateToken(user) }
            .map { jwtToken -> AuthenticationResponse(jwtToken) }
    }

    fun authenticate(request: AuthenticationRequest): Single<AuthenticationResponse> {
        return Single
            .fromCallable { UsernamePasswordAuthenticationToken(request.email, request.password) }
            .map { authenticationManager.authenticate(it) }
            .map { userRepository.findByEmail(request.email).orElseThrow() }
            .map { jwtService.generateToken(it) }
            .map { jwtToken -> AuthenticationResponse(jwtToken) }
    }
}