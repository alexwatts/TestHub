package com.alwa.testhub.report

import com.alwa.testhub.domain.ReportData
import com.alwa.ObjectMother
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant

class CombinedReportBuilderTest {

    val subject = CombinedReportParser()

    val cucumberReportData =
        ReportData(
            Instant.parse("2020-05-20T09:00:00Z"),
            ObjectMother.report(),
            "testPartition")

    val cypressReportData =
        ReportData(
            Instant.parse("2020-05-20T09:00:00Z"),
            ObjectMother.cypressReport(),
            "testPartition")


    val expectedCucumberTestNames = listOf("Test Example")
    val expectedCypressTestNames = listOf("Sidebar Functionality")

    @Test
    fun shouldParseACumcumberReport() {
        val testResults = subject.parseTestResults(cucumberReportData)
        assertThat(testResults.map { it.name }.toList(), equalTo(expectedCucumberTestNames))
    }

    @Test
    fun shouldParseACypressReport() {
        val testResults = subject.parseTestResults(cypressReportData)
        assertThat(testResults.map { it.name }.toList(), equalTo(expectedCypressTestNames))
    }

}
