package com.example.demo.util

import com.example.demo.model.JobInfo
import java.sql.DriverManager
import java.sql.ResultSet

object DbTool {
    private const val URL = "jdbc:postgresql://postgres:5432/postgres"
    private const val USERNAME = "pgadmin"
    private const val PASSWORD = "pgadmin"

    fun getJobInfo(jobName: String): JobInfo? {
        var jobInfo: JobInfo? = null
        DriverManager.getConnection(URL, USERNAME, PASSWORD).use { connection ->
            val sql = "SELECT * FROM erp.job_log WHERE jobname = ?"
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, jobName)
                val resultSet = statement.executeQuery()
                if (resultSet.next()) {
                    jobInfo = JobInfo(
                        flowno = resultSet.getInt("flowno"),
                        startdt = resultSet.getTimestamp("startdt"),
                        enddt = resultSet.getTimestamp("enddt"),
                        status = resultSet.getString("status"),
                        jobname = resultSet.getString("jobname"),
                        description = resultSet.getString("description")
                    )
                }
            }
        }
        return jobInfo
    }

    fun getRowData(sql: String, parameters: List<Any>): Map<String, Any>? {
//        List<Map<String, Any>>
        DriverManager.getConnection(URL, USERNAME, PASSWORD).use { connection ->
            connection.prepareStatement(sql).use { statement ->
                parameters.forEachIndexed { index, param ->
                    statement.setObject(index + 1, param)
                }
                val resultSet = statement.executeQuery()
                val metaData = resultSet.metaData
                val columnCount = metaData.columnCount
                while (resultSet.next()) {
                    val row = mutableMapOf<String, Any>()
                    for (i in 1..columnCount) {
                        val columnName = metaData.getColumnName(i)
                        val columnValue = resultSet.getObject(i)
                        row[columnName] = columnValue
                    }
                    return row
                    //resultData.add(row)
                }
            }
        }
        return null
    }

    fun insetData(any: Any) {
        DriverManager.getConnection(URL, USERNAME, PASSWORD).use { connection ->
            // 插入數據
            //insertJobLog(connection, any)
        }
    }
}