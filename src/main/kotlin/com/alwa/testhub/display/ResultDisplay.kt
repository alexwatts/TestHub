package com.alwa.testhub.display

import com.alwa.testhub.domain.*
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ResultDisplay {

    fun displayResults(displayName: String,  results: List<TestResult>) =
        ReportDisplay(
            displayName,
            buildHeaderRow(results) + buildTestRows(results)
        )

    private fun buildHeaderRow(results: List<TestResult>) =
        listOf(Row("header", buildHeaderColumns(results)))

    private fun buildTestRows(results: List<TestResult>) =
        results
            .distinctBy { it.name }
            .map { Row(it.name, buildTestColumns(results, it)) }

    private fun buildTestColumns(results: List<TestResult>, testName: TestResult) =
        results
            .sortedByDescending { it.reportTime }
            .groupBy { it.reportTime }
            .map { findTestRun(it.key, testName, results) }
            .map { toColumn(it) }

    private fun findTestRun(
        testRun: Instant,
        testName: TestResult,
        results: List<TestResult>) =
            results
                .filter { it.name == testName.name }
                .find { it.reportTime == testRun }

    private fun buildHeaderColumns(results: List<TestResult>) =
        results
            .distinctBy { it.reportTime }
            .sortedByDescending { it.reportTime }
            .map { Column(formatDate(it.reportTime), null, emptyList(), emptyList()) }

    private fun toColumn(testResult: TestResult?): Column =
       testResult?.let { displayColumn(it) } ?: Column("empty", null, emptyList(), emptyList())

    private fun displayColumn(testResult: TestResult) =
        if (testResult.success) passedColumn(testResult) else failedColumn(testResult)

    private fun passedColumn(testResult: TestResult) =
        Column("passed", testResult.imageOrNull(), emptyList(), emptyList())

    private fun failedColumn(testResult: TestResult) =
        Column("failed", testResult.imageOrNull(), testResult.messages, testResult.properties)

    private fun TestResult.imageOrNull() =
       this.screenShot?.let { Image("screenshot", it.mimeType, it.data) }

    private fun formatDate(date: Instant) = formatter().format( date )

    private fun formatter(): DateTimeFormatter =
        DateTimeFormatter.ISO_LOCAL_DATE_TIME
            .withZone(ZoneId.from(ZoneOffset.UTC))

}
