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

    fun createReport(reportData: String, group: String) {
        reportRepository.create(ReportData(clock.instant(), reportData, group))
    }

    fun getReports(groups: Array<String>?) =
        ResultDisplay(groupsOrDefault(groups)).displayResults(
            reportRepository.getAfter(window())
            .values
            .flatten()
            .map { reportBuilder.parseTestResults(it) }
            .flatten()
        )

    fun delete(group: String) = reportRepository.delete(group)

    fun getGroups() =
        reportRepository.getAfter(window())
            .map { it.value }
            .flatten()
            .map { it.group }.distinct()

    private fun groupsOrDefault(groups: Array<String>?) =
        when (groups?.size) {
            0    -> defaultGroup()
            else -> groups?.toList()
        } ?: defaultGroup()

    private fun defaultGroup() = listOf("default")

    private fun window() =
        clock.instant().minus(5, ChronoUnit.DAYS)
}
