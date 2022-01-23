package com.alwa.testhub.repository

import com.alwa.testhub.domain.ReportData
import java.time.Instant

interface ReportRepository {
    fun create(reportData: ReportData)
    fun getBefore(before: Instant): Map<String, List<ReportData>>
    fun getAfter(after: Instant): Map<String, List<ReportData>>
    fun delete(partition: String)
}
