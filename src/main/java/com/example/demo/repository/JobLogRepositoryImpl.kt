package com.example.demo.repository

import com.example.demo.entity.JobLog
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager

class JobLogRepositoryImpl(
    @Autowired private val entityManager: EntityManager
) {

//    fun findByJobName(jobName: String): JobLog {
//        // 可以直接调用 JobLogRepository 的方法
//        return jobLogRepository.findByJobName(jobName)
//    }

    // 实现自定义的查询方法
    fun findByJobName(jobName: String): JobLog {
        // 自定义查询逻辑
        // 例如：
        // return jobLogRepository.findByStatus("Completed")
        // 或者直接使用自定义的查询语句
        val sql = "SELECT j.flowno, j.startdt,j.enddt,j.status,j.jobname,j.description " +
                "FROM JobLog j WHERE j.jobname = :jobName"
        val query = entityManager.createQuery(sql, JobLog::class.java).setParameter("jobName", jobName)
        return query.singleResult as JobLog
//        return jobLogRepository.findByJobName(jobName)
    }
}