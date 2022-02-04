package com.alwa.testhub.display

import com.alwa.testhub.domain.*
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ResultDisplay {

    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC))

    fun displayResults(results: List<TestResult>): ReportDisplay {
        return ReportDisplay(
            listOf(
                    buildHeaderRow(results)
                  ) + buildTestRows(results),
        )
    }

    private fun buildHeaderRow(results: List<TestResult>) =
        Row("header", buildHeaderColumns(results))

    private fun buildTestRows(results: List<TestResult>) =
        results
            .sortedByDescending { it.reportTime }
            .distinctBy { it.name }
            .map { testName ->
                Row(
                    testName.name,
                    buildTestColumns(results, testName)
                )
            }

    private fun buildTestColumns(results: List<TestResult>, testName: TestResult) =
        results
            .sortedByDescending { it.reportTime }
            .groupBy { it.reportTime }
            .map { testRun ->
                    results
                        .partition { it.name == testName.name }
                        .first
                        .groupBy { it.reportTime }
                        .get(testRun.key)
            }.map { toColumn(it) }

    private fun buildHeaderColumns(results: List<TestResult>) =
        results
            .distinctBy { it.reportTime }
            .sortedByDescending { it.reportTime }
            .map { Column(formatDate(it.reportTime), null) }

    private fun toColumn(testResults: List<TestResult>?) : Column {
        testResults.withNotNullNorEmpty {
            return displayValue(testResults) ?: Column("empty", null)
        }
        testResults.whenNotNullNorEmpty {
            return Column("empty", null)
        }
        return Column("empty", null)
    }

    private fun displayValue(testResult: List<TestResult>?) =
        testResult.let { result -> result?.map { if (it.success) passedColumn(it) else failedColumn(it) }}?.firstOrNull()

    private fun passedColumn(testResult: TestResult) =
        Column("passed", testResult.imageOrNull())

    private fun failedColumn(testResult: TestResult) =
        Column("failed", testResult.imageOrNull())

    private fun TestResult.imageOrNull() =
       this.screenShot?.let { Image("screenshot", it.mimeType, it.data) }

    private fun formatDate(date: Instant) = formatter.format( date )

    private inline fun <E: Any, T: Collection<E>> T?.withNotNullNorEmpty(func: T.() -> Unit): Unit {
        if (this != null && this.isNotEmpty()) {
            with (this) { func() }

        }
    }

    private inline fun  <E: Any, T: Collection<E>> T?.whenNotNullNorEmpty(func: (T) -> Unit): Unit {
        if (this != null && this.isNotEmpty()) {
            func(this)
        }
    }

}
