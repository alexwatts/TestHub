package com.alwa.testhub.report

import com.alwa.testhub.domain.ReportData
import com.alwa.testhub.domain.TestResult
import org.springframework.stereotype.Service

@Service
class CombinedReportParser: ReportParser {

    private val reportParsers = listOf(
        SimpleCucumberReportParser(),
        SimpleCypressReportParser())

    override fun parseTestResults(reportData: ReportData) : List<TestResult> =
        reportParsers
            .filter { isValid(it, reportData) }
            .flatMap { it.parseTestResults(reportData) }

    private fun isValid(reportParser: ReportParser, reportData: ReportData) =
        try { reportParser.parseTestResults(reportData).isNotEmpty()} catch (e: Exception) { false }

}