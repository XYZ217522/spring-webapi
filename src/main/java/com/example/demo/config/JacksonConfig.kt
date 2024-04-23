package com.example.demo.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.text.SimpleDateFormat

/**
 *  for 讓時間格式轉成物件不會出錯
 * */

@Configuration
open class JacksonConfig {

    @Bean
    open fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        objectMapper.dateFormat = dateFormat
        return objectMapper
    }
}
