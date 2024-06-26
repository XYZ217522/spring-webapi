package com.example.demo.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.util.*

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

    @field:NotBlank val status: String? = null,

    @field:NotBlank val jobname: String? = null,

    val description: String? = null,

    @Temporal(TemporalType.TIMESTAMP)
    var update_time: Date? = null
)