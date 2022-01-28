package com.alwa.testhub.service

import com.alwa.testhub.display.ResultDisplay
import com.alwa.testhub.domain.ReportData
import com.alwa.testhub.report.ReportParser
import com.alwa.testhub.repository.ReportRepository
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.temporal.ChronoUnit

@Service
class ReportService(
    private val reportRepository: ReportRepository,
    private val clock: Clock,
    private val reportBuilder: ReportParser) {

    fun createReport(reportData: String, partition: String) {
        reportRepository.create(ReportData(clock.instant(), reportData, partition))
    }

    fun getReports() =
        ResultDisplay().displayResults(
            reportRepository.getAfter(fiveDaysAgo())
            .values
            .flatten()
            .map { reportBuilder.parseTestResults(it) }
            .flatten()
            .sortedByDescending { it.reportTime }
        )

    fun delete(partition: String) = reportRepository.delete(partition)

    private fun fiveDaysAgo() =
        clock.instant().minus(5, ChronoUnit.DAYS)
}
