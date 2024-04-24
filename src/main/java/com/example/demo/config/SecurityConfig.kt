package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
open class SecurityConfig {

    // 允許所有用戶訪問，即不需要進行身份驗證。authenticated
    val nonAuthenticatedApis = listOf(
        /** job **/
        "/job/getAll",
        "/job/rxjava/{jobName}",
        "/job/{jobName}",
    )

    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf()
            .disable()
            .authorizeHttpRequests { authorize ->
                authorize
                    .antMatchers(HttpMethod.GET) // 所有GET都不用認證
                    .permitAll()
                    .anyRequest()
                    .authenticated() // 其他需要認證
            }
            .build()
    }
}