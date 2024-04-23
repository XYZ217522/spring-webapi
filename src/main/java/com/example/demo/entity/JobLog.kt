package com.example.demo.entity

import java.util.Date
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name = "job_log", schema = "erp")
data class JobLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val flowno: Long = 0,

    @NotNull(message = "startdt is mandatory")
    @NotEmpty(message = "startdt is mandatory")
    @Temporal(TemporalType.TIMESTAMP)
    val startdt: Date = Date(1970, 1, 1),

    @Temporal(TemporalType.TIMESTAMP)
    val enddt: Date = Date(1970, 1, 1),

    val status: String? = null,

    @NotNull(message = "jobname is mandatory")
    @NotEmpty(message = "jobname is mandatory")
    val jobname: String? = null,

    val description: String? = null
)