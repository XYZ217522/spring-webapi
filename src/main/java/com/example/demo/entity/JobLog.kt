package com.example.demo.entity

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "job_log", schema = "erp")
data class JobLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val flowno: Long = 0,

    @Temporal(TemporalType.TIMESTAMP)
    val startdt: Date = Date(1970, 1, 1),

    @Temporal(TemporalType.TIMESTAMP)
    val enddt: Date = Date(1970, 1, 1),

    val status: String? = null,

    val jobname: String? = null,

    val description: String? = null
)