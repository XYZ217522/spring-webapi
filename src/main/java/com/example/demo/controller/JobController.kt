package com.example.demo.controller

import com.example.demo.service.JobRxJavaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("job")
class JobController {

    @Autowired
    lateinit var userRxJavaService: JobRxJavaService

    @GetMapping("getAll")
    fun getAll(): ResponseEntity<String> {
        return ResponseEntity.ok("response: job/getAll")
    }

    @GetMapping("rxjava/{jobName}")
    fun getJobInfo(@PathVariable jobName: String): ResponseEntity<String> {
        return ResponseEntity.ok("response:${userRxJavaService.findByName(jobName)}")
        //return ResponseEntity.ok("response: job/rxjava/jobname=$jobname")
    }


}