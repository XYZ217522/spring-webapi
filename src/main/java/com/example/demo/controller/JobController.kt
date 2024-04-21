package com.example.demo.controller

import com.example.demo.model.JobInfo
import com.example.demo.service.JobRxJavaService
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.rxjava3.core.Flowable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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
    fun getJobInfo(@PathVariable jobName: String): ResponseEntity<Flowable<String>> {
        //return ResponseEntity.ok("response:${userRxJavaService.findByName(jobName)}")
        //return ResponseEntity.ok("response: job/rxjava/jobname=$jobname")
        val jobFlowable = userRxJavaService
            .findByNameUseRX(jobName)
            .map { ObjectMapper().writeValueAsString(it) }
        return ResponseEntity(jobFlowable, HttpStatus.OK)
    }

}