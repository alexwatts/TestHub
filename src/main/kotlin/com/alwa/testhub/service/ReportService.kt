package com.alwa.testhub.service

import com.alwa.testhub.controller.Parameters.DEFAULT_GROUP
import com.alwa.testhub.display.ResultDisplay
import com.alwa.testhub.domain.ReportData
import com.alwa.testhub.domain.TestResult
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

    fun createReport(reportData: String, group: String) {
        reportRepository.create(ReportData(clock.instant(), reportData, group))
    }

    fun getReports(groups: List<String>) =
        when(groups) {
            listOf(DEFAULT_GROUP) -> listOf(allReports(reportRepository.getAfter(window())))
            else                  -> groupedReports(groups, reportRepository.getAfter(window()))
        }

    fun delete(group: String) = reportRepository.delete(group)

    fun getGroups() =
        reportRepository.getAfter(window())
            .map { it.value }
            .flatten()
            .map { it.group }.distinct()

    private fun allReports(reports: Map<String, List<ReportData>>) =
        ResultDisplay().displayResults(
            DEFAULT_GROUP,
            reports
                .values
                .flatten()
                .map { reportBuilder.parseTestResults(it) }
                .flatten()
        )

    private fun groupedReports(groups: List<String>, reports: Map<String, List<ReportData>>) =
        groups.map { group ->
            ResultDisplay().displayResults(
                group,
                reports
                    .values
                    .flatten()
                    .map { reportBuilder.parseTestResults(it).groupFilter(group) }
                    .flatten()
            )
        }

    private fun window() =
        clock.instant().minus(5, ChronoUnit.DAYS)

    private fun List<TestResult>.groupFilter(group: String) =
        this.filter { group == DEFAULT_GROUP || it.group == group }

}
