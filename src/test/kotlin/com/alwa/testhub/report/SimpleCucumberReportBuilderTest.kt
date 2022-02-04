package com.alwa.testhub.report

import com.alwa.testhub.domain.ReportData
import com.alwa.ObjectMother
import com.alwa.testhub.domain.ScreenShot
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant

class SimpleCucumberReportBuilderTest {

    val subject = SimpleCucumberReportParser()

    val reportData =
        ReportData(
            Instant.parse("2020-05-20T09:00:00Z"),
            ObjectMother.report(),
            "testPartition")

    val failedReportData =
        ReportData(
            Instant.parse("2020-05-20T09:00:00Z"),
            ObjectMother.failedReport(),
            "testPartition")

    val expectedTestNames = listOf("Test Example")
    val expectedReportTime = listOf(Instant.parse("2020-05-20T09:00:00Z"))
    val expectedScreenShot = ScreenShot("gif;base64", "guehnfdsaghl545423hbkj34lknb5hk34")

    @Test
    fun testNameIsSourcedFromReport() {
        val testResults = subject.parseTestResults(reportData)
        assertThat(testResults.map { it.name }.toList(), equalTo(expectedTestNames))
    }

    @Test
    fun testReportTimeIsDisplayedAsSubmitted() {
        val testResults = subject.parseTestResults(reportData)
        assertThat(testResults.map { it.reportTime }.toList(), equalTo(expectedReportTime))
    }

    @Test
    fun passingTestHasSuccessfulResult() {
        val testResults = subject.parseTestResults(reportData)
        assertThat(testResults.map { it.success }.first(), equalTo(true))
    }

    @Test
    fun failingTestHasFailedResult() {
        val testResults = subject.parseTestResults(failedReportData)
        assertThat(testResults.map { it.success }.first(), equalTo(false))
    }

    @Test
    fun testScreenShotIsSourcedFromReport() {
        val testResults = subject.parseTestResults(failedReportData)
        assertThat(testResults.map { it.screenShot }.first(), equalTo(expectedScreenShot))
    }
}
