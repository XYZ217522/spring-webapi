package com.example.demo.controller

import com.example.demo.entity.JobLog
import com.example.demo.service.JobRxJavaService
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.rxjava3.core.Flowable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("job")
class JobController {

    @Autowired
    lateinit var jobRxJavaService: JobRxJavaService

    @GetMapping("getAll")
    fun getAll(): ResponseEntity<Flowable<List<JobLog>>> {
        return ResponseEntity(jobRxJavaService.findAll(), HttpStatus.OK)
    }

    @GetMapping("rxjava/{jobName}")
    fun getJobInfo(@PathVariable jobName: String): ResponseEntity<Flowable<String>> {
        //return ResponseEntity.ok("response:${userRxJavaService.findByName(jobName)}")
        //return ResponseEntity.ok("response: job/rxjava/jobname=$jobname")
        val jobFlowable = jobRxJavaService
            .findByNameUseRX(jobName)
            .map { ObjectMapper().writeValueAsString(it) }
        return ResponseEntity(jobFlowable, HttpStatus.OK)
    }

    @GetMapping("{jobName}")
    fun getJobLogInfo(@PathVariable jobName: String): ResponseEntity<Flowable<JobLog>> {
        val jobFlowable = jobRxJavaService.findByName(jobName)
        return ResponseEntity(jobFlowable, HttpStatus.OK)
    }

    @PostMapping("add")
    fun addJob(@RequestBody jobLog: JobLog): ResponseEntity<Flowable<String>> {
        println("requestBody = $jobLog")
        return ResponseEntity.ok(jobRxJavaService.addJobLog(jobLog))
    }

    @DeleteMapping("byName/{jobName}")
    fun deleteJobName(@PathVariable jobName: String): ResponseEntity<Flowable<String>> {
        println("jobName = $jobName")
        return ResponseEntity.ok(jobRxJavaService.deleteJobLog(jobName))
    }

    @DeleteMapping("byID/{flowno}")
    fun deleteJobById(@PathVariable flowno: Long): ResponseEntity<Flowable<String>> {
        println("flowno = $flowno")
        return ResponseEntity.ok(jobRxJavaService.deleteJobLog(flowno))
    }
}