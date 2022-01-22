package com.alwa.testhub.domain

import com.alwa.testhub.report.ReportParser
import java.time.Instant

class CucumberReport(
    val reportData: ReportData,
    val cucumberReportBuilder: ReportParser
): Report {

    override fun getTime(): Instant {
        return reportData.time
    }

    override fun getTestResults(): List<TestResult> {
        return cucumberReportBuilder.parseTestResults(reportData)
    }
}
