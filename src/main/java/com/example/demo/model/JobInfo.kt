package com.example.demo.model

import java.sql.Timestamp

//CREATE TABLE erp.job_log (
//            flowno SERIAL PRIMARY KEY,
//            startdt TIMESTAMP,
//            enddt TIMESTAMP,
//            status VARCHAR(10),
//            jobname VARCHAR(50),
//            description VARCHAR(100)
//        );
data class JobInfo(
    val flowno: Int,
    val startdt: Timestamp,
    val enddt: Timestamp,
    val status: String,
    val jobname: String,
    val description: String
)

fun Map<String, Any>.createByDB(): JobInfo {
    return JobInfo(
        flowno = this["flowno"] as Int,
        startdt = this["startdt"] as Timestamp,
        enddt = this["enddt"] as Timestamp,
        status = this["status"] as String,
        jobname = this["jobname"] as String,
        description = this["description"] as String
    )
}
