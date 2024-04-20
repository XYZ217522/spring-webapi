package com.example.demo.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("job")
class JobController {

    @GetMapping("getAll")
    fun getAll(): ResponseEntity<String> {

        return ResponseEntity.ok("response: job/getAll")
    }
}