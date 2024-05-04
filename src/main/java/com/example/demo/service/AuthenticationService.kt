package com.example.demo.service

import com.example.demo.auth.AuthenticationRequest
import com.example.demo.auth.AuthenticationResponse
import com.example.demo.auth.RegisterRequest
import com.example.demo.entity.JobLog
import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.repository.UserRepository
import com.example.demo.validator.ObjectValidator
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val validator: ObjectValidator<RegisterRequest>
) {

    fun register(request: RegisterRequest): Single<AuthenticationResponse> {
        validator.validate(request)
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
            .observeOn(Schedulers.io())
            .map { user -> userRepository.save(user) }
            .map { user: User -> AuthenticationResponse(jwtService.generateToken(user)) }
            .onErrorResumeNext { Single.error(IllegalArgumentException(it.message)) }
    }

    fun authenticate(request: AuthenticationRequest): Single<AuthenticationResponse> {
//        validator.validate(request)
        return Single
            .fromCallable { UsernamePasswordAuthenticationToken(request.email, request.password) }
            .map { authenticationManager.authenticate(it) }
            .observeOn(Schedulers.io())
            .map { userRepository.findByEmail(request.email).get() }
            .map { AuthenticationResponse(jwtService.generateToken(it)) }
            .onErrorResumeNext { Single.error(IllegalArgumentException(it.message)) }
    }
}