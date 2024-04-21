package com.example.demo.service

import com.example.demo.model.JobInfo
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
class JobRxJavaService {

//    @Autowired
//    lateinit var jobRepository: JobRxJavaRepository

    // Flowable<JobInfo>
    fun findByName(jobName: String): JobInfo {
        // jobRepository.findUserByName(name)
        return JobInfo(
            flowno = 1,
            startdt = Timestamp.valueOf("2024-04-21 16:20:00"),
            enddt = Timestamp.valueOf("2024-04-21 16:22:00"),
            starus = "done",
            jobname = "test job",
            description = "this is test job"
        )
    }

}