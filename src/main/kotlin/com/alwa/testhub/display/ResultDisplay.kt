package com.alwa.testhub.display

import com.alwa.testhub.domain.Column
import com.alwa.testhub.domain.ReportDisplay
import com.alwa.testhub.domain.Row
import com.alwa.testhub.domain.TestResult
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ResultDisplay {

    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC))

    fun displayResults(results: List<TestResult>): ReportDisplay {
        return ReportDisplay(
            listOf(
                    buildHeaderRow(results.sortedByDescending { it.reportTime })
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
            .sortedBy { it.reportTime }
            .map { Column(formatDate(it.reportTime)) }

    private fun toColumn(testResults: List<TestResult>?) : Column {
        testResults.withNotNullNorEmpty {
            return if (this.first().success) Column("passed") else Column("failed")
        }
        testResults.whenNotNullNorEmpty {
            return Column("empty")
        }
        return Column("empty")
    }

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
