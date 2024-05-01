package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
open class SecurityConfig(
    private val authenticationProvider: AuthenticationProvider,
    private val jwtAuthFilter: JwtAuthenticationFilter,
) {

    // 允許所有用戶訪問，即不需要進行身份驗證。authenticated
    private val whiteListUrls = arrayOf(
        "/job/getAll",
        "/job/rxjava/{jobName}",
        "/job/{jobName}",
        "/auth/**"
    )


    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http.invoke {
//            authorizeRequests {
//                authorize(POST, "/job/add", "ADMIN")
//                authorize(DELETE, "/job/byID/**", "ADMIN")
//            }
//            httpBasic {}
//            sessionManagement {
//                sessionCreationPolicy
//            }
//        }
//
//
//        return http.build()
        return http
            .csrf { csrfCustomizer -> csrfCustomizer.disable() }
            .authorizeHttpRequests { req ->
                req
                    .requestMatchers(*whiteListUrls).permitAll()
                    .requestMatchers(POST, "/job/add").hasRole("ADMIN")
                    .requestMatchers(DELETE, "/job/byName/**").hasRole("ADMIN")
                    .requestMatchers(DELETE, "/job/byID/**").hasRole("ADMIN")
                    .anyRequest()
                    .authenticated() // 其他需要認證
            }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}