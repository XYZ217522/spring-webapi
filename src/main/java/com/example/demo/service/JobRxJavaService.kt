package com.example.demo.service

import com.example.demo.model.JobInfo
import com.example.demo.model.createByDB
import com.example.demo.util.DbTool
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import org.springframework.stereotype.Component
import java.sql.Timestamp


@Component
class JobRxJavaService {

    //    @Autowired
//    lateinit var jobRepository: JobRxJavaRepository

    // Flowable<JobInfo>
    fun findByName(jobName: String): Flowable<JobInfo> {
        // jobRepository.findUserByName(name)
        return Flowable.just(
            JobInfo(
                flowno = 1,
                startdt = Timestamp.valueOf("2024-04-21 16:20:00"),
                enddt = Timestamp.valueOf("2024-04-21 16:22:00"),
                status = "done",
                jobname = "test job",
                description = "this is test job"
            )
        )
    }

    fun findByNameUseRX(jobName: String): Flowable<JobInfo> {
        return Flowable.create({ emitter ->
            val sql = "SELECT * FROM erp.job_log WHERE jobname = ?"
            val params = listOf(jobName)
            val jobInfo = DbTool.getRowData(sql, params)?.createByDB() ?: throw Exception("query no found!")
            emitter.onNext(jobInfo)
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }

}