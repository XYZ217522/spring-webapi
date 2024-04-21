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
    val starus: String,
    val jobname: String,
    val description: String
)
