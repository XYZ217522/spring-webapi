package com.example.demo.service

import com.example.demo.entity.JobLog
import com.example.demo.model.JobInfo
import com.example.demo.model.createByDB
import com.example.demo.repository.JobLogRepository
import com.example.demo.util.DbTool
import com.example.demo.validator.ObjectValidator
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import toJSONStr


@Service
class JobRxJavaService(
    private val jobLogRepository: JobLogRepository,
    private val jobLogValidator: ObjectValidator<JobLog>
) {

    fun findByNameUseRX(jobName: String): Flowable<JobInfo> {
        return Flowable.create({ emitter ->
            val sql = "SELECT * FROM erp.job_log WHERE jobname = ?"
            val params = listOf(jobName)
            val jobInfo = DbTool.getRowData(sql, params)?.createByDB() ?: throw Exception("query no found!")
            emitter.onNext(jobInfo)
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }

    fun findByName(jobName: String): Flowable<JobLog> {
        return Flowable
            .fromCallable { jobLogRepository.findByJobName(jobName) ?: throw EntityNotFoundException() }
            .onErrorResumeNext { return@onErrorResumeNext Flowable.error(EntityNotFoundException()) }
    }

    fun findAll(): Flowable<List<JobLog>> {
        return Flowable.create({ emitter ->
            val data: List<JobLog>? = jobLogRepository.findAll()
            data?.let {
                emitter.onNext(it)
                emitter.onComplete()
            } ?: emitter.onError(EntityNotFoundException())
        }, BackpressureStrategy.BUFFER)
    }

    fun addJobLog(jobLog: JobLog): Flowable<String> {
        println("jobLog = $jobLog")
        jobLogValidator.validate(jobLog)
        return Flowable
            .fromCallable { jobLogRepository.save(jobLog) }
            .flatMap { Flowable.just(mutableMapOf("status" to "success").toJSONStr()) }
            .onErrorResumeNext { Flowable.error(IllegalArgumentException()) }
    }

    fun deleteJobLog(jobName: String): Flowable<String> {
        return findByName(jobName)
            .flatMapCompletable { jobLog -> Completable.fromAction { jobLogRepository.delete(jobLog) } }
            .toSingleDefault(mutableMapOf("status" to "success").toJSONStr())
            .toFlowable()
            .onErrorResumeNext {
                return@onErrorResumeNext Flowable.error(IllegalArgumentException())
            }
    }

    fun deleteJobLog(flowno: Long): Flowable<String> {
        return Flowable
            .fromCallable { jobLogRepository.findById(flowno).get() }
            .flatMapCompletable { jobLog -> Completable.fromAction { jobLogRepository.delete(jobLog) } }
            .toSingleDefault(mutableMapOf("status" to "success").toJSONStr())
            .toFlowable()
            .onErrorResumeNext {
                if (it is NoSuchElementException) return@onErrorResumeNext Flowable.error(it)
                return@onErrorResumeNext Flowable.error(IllegalArgumentException())
            }
    }
}