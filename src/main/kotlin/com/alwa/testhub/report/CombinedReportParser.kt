package com.alwa.testhub.report

import com.alwa.testhub.domain.ReportData
import com.alwa.testhub.domain.TestResult
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CombinedReportParser: ReportParser {

    private val reportParsers = listOf(
        SimpleCucumberReportParser(),
        SimpleCypressReportParser())

    override fun parseTestResults(reportData: ReportData) : List<TestResult> =
        reportParsers
            .parallelStream()
            .filter { isValid(it, reportData) }
            .flatMap { it.parseTestResults(reportData).stream() }
            .collect(Collectors.toList())

    private fun isValid(reportParser: ReportParser, reportData: ReportData) =
        try { reportParser.parseTestResults(reportData).isNotEmpty()} catch (e: Exception) { false }

}