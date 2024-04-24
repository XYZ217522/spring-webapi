package com.example.demo.repository

import com.example.demo.entity.JobLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface JobLogRepository: JpaRepository<JobLog, Long> {

    @Query(value = "SELECT * FROM erp.job_log WHERE jobname = :jobName", nativeQuery = true)
    fun findByJobName(jobName: String): JobLog?
}