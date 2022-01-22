package com.alwa.testhub.report

import com.alwa.testhub.domain.ReportData
import com.alwa.testhub.domain.Test
import com.alwa.testhub.domain.TestResult

interface ReportParser {
    fun parseTestResults(reportData: ReportData): List<TestResult>
}
